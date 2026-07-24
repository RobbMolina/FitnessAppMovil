import retrofit2.http.GET
import retrofit2.http.Query
import robb.stark.proyectomobil.proyectomobil.models.Exercise

interface ApiInterface {
    @GET("exercises")
    suspend fun getExercises(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): List<Exercise>
}