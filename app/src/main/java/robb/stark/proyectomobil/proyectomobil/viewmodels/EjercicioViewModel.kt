package robb.stark.proyectomobil.proyectomobil.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import robb.stark.proyectomobil.proyectomobil.API.domain.API
import robb.stark.proyectomobil.proyectomobil.models.Exercise

/** Un día del plan semanal fijo: qué músculos toca, o si es día de descanso. */
data class DiaPlan(
    val dia: String,
    val grupoMuscular: String,
    val bodyParts: List<String> = emptyList(),
    val esDescanso: Boolean = false
)

/** Un día ya resuelto con los ejercicios reales que le tocaron, listo para pintar en la UI. */
data class DiaConEjercicios(
    val dia: String,
    val grupoMuscular: String,
    val esDescanso: Boolean,
    val ejercicios: List<Exercise>
)

class EjercicioViewModel : ViewModel() {
    var ejercicios by mutableStateOf<List<Exercise>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    private var yaCargado = false

    fun traerEjercicios() {
        // Si ya se cargó el catálogo (o hay una carga en curso), no se vuelve a
        // pedir todo de nuevo cada vez que se presiona el botón o se reentra a la pantalla.
        if (yaCargado || isLoading) return

        isLoading = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ejercicios = API().getEjercicios()
            }
            yaCargado = ejercicios.isNotEmpty()
            isLoading = false
        }
    }

    companion object {
        /** Cuántos ejercicios como máximo se muestran por día, para no saturar la pantalla. */
        private const val MAX_EJERCICIOS_POR_DIA = 5

        /**
         * Plan semanal fijo (split por grupo muscular, 5 días de entreno + 2 de descanso).
         * Los valores de bodyParts deben coincidir con los que devuelve ExerciseDB
         * (chest, back, upper legs, lower legs, shoulders, waist, upper arms, lower arms,
         * neck, cardio).
         */
        private val PLAN_SEMANAL = listOf(
            DiaPlan("Monday", "Chest & triceps", listOf("chest", "upper arms")),
            DiaPlan("Tuesday", "Back & biceps", listOf("back", "lower arms")),
            DiaPlan("Wednesday", "Legs", listOf("upper legs", "lower legs")),
            DiaPlan("Thursday", "Shoulders & abs", listOf("shoulders", "waist")),
            DiaPlan("Friday", "Cardio & full body", listOf("cardio", "neck")),
            DiaPlan("Saturday", "Rest", esDescanso = true),
            DiaPlan("Sunday", "Rest", esDescanso = true)
        )
    }

    /**
     * Toma los ejercicios ya cargados de la API y los reparte en el plan semanal
     * filtrando por bodyPart. Se recalcula automáticamente cada vez que [ejercicios] cambia.
     */
    val rutinaSemanal: List<DiaConEjercicios>
        get() = PLAN_SEMANAL.map { diaPlan ->
            val ejerciciosDelDia = if (diaPlan.esDescanso) {
                emptyList()
            } else {
                ejercicios
                    .filter { ejercicio ->
                        diaPlan.bodyParts.any { it.equals(ejercicio.bodyPart.trim(), ignoreCase = true) }
                    }
                    .take(MAX_EJERCICIOS_POR_DIA)
            }
            DiaConEjercicios(
                dia = diaPlan.dia,
                grupoMuscular = diaPlan.grupoMuscular,
                esDescanso = diaPlan.esDescanso,
                ejercicios = ejerciciosDelDia
            )
        }
}