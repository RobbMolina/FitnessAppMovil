package robb.stark.proyectomobil.proyectomobil.models

data class ExerciseImage(
    val id: Int,
    val exercise: Int,
    val image: String
)

data class ExerciseImageResponse(
    val results: List<ExerciseImage>
)
