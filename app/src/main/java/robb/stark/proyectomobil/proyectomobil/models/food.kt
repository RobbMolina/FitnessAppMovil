package robb.stark.proyectomobil.proyectomobil.models

import androidx.annotation.DrawableRes

data class food(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val cals: Float,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    @DrawableRes val img: Int
)