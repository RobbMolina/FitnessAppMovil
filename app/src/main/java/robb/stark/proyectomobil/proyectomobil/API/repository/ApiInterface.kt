import retrofit2.http.GET
import robb.stark.proyectomobil.proyectomobil.models.Exercise

interface ApiInterface {
    @GET("exercises")
    suspend fun getExercises(): List<Exercise>
}
