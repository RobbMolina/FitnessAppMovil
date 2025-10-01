package robb.stark.proyectomobil.proyectomobil.API.domain

import robb.stark.proyectomobil.proyectomobil.API.repository.RetroFitHelper
import robb.stark.proyectomobil.proyectomobil.models.Exercise

class API {
    private val service = RetroFitHelper.getRetrofitService()

    suspend fun getEjercicios(): List<Exercise> {
        return try {
            service.getExercises()
        } catch (e: Exception) {
            emptyList()
        }
    }
}
