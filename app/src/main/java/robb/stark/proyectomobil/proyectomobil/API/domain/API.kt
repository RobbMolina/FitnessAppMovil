package robb.stark.proyectomobil.proyectomobil.API.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import robb.stark.proyectomobil.proyectomobil.API.repository.RetroFitHelper
import robb.stark.proyectomobil.proyectomobil.models.Exercise

class API {
    private val service = RetroFitHelper.getRetrofitService()

    /**
     * La API solo devuelve una "página" de ejercicios por llamada, así que se
     * piden varias páginas para juntar el catálogo completo. A diferencia de
     * pedirlas una por una (lento, se suman las latencias), aquí se piden en
     * bloques de [concurrencia] páginas AL MISMO TIEMPO, lo que reduce el
     * tiempo total de carga varias veces. Se detiene al encontrar una página
     * más corta que [pageSize] (última página real) o al llegar a [maxPages]
     * como red de seguridad.
     */
    suspend fun getEjercicios(): List<Exercise> {
        val pageSize = 100
        val maxPages = 15       // tope de seguridad: ~1500 ejercicios
        val concurrencia = 5    // páginas pedidas en paralelo por bloque
        val resultado = mutableListOf<Exercise>()

        return try {
            coroutineScope {
                var pageIndex = 0
                bloques@ while (pageIndex < maxPages) {
                    val offsetsDelBloque = pageIndex until minOf(pageIndex + concurrencia, maxPages)
                    val bloque = offsetsDelBloque.map { i ->
                        async { service.getExercises(limit = pageSize, offset = i * pageSize) }
                    }.awaitAll()

                    for (pagina in bloque) {
                        resultado.addAll(pagina)
                        if (pagina.size < pageSize) break@bloques
                    }
                    pageIndex += concurrencia
                }
            }
            resultado
        } catch (e: Exception) {
            // Si falla a media carga, devolvemos lo que ya se alcanzó a juntar.
            resultado
        }
    }
}