package robb.stark.proyectomobil.proyectomobil.models

/**
 * Documento que se guarda en Firestore bajo users/{uid}.
 * Todos los campos tienen valor por defecto porque Firestore necesita
 * un constructor vacío para poder deserializar el documento con toObject().
 */
data class UserProfile(
    val uid: String = "",
    val username: String = "",
    val email: String = "",

    // Datos personales (llenados en Calculadora)
    val age: Int = 0,
    val height: Float = 0f,
    val weight: Float = 0f,
    val activity: String = "",
    val goal: String = "",

    // Metas diarias calculadas
    val kcal: Float = 0f,
    val prote: Float = 0f,
    val grasas: Float = 0f,
    val carbs: Float = 0f,

    // Progreso del día (lo que ya consumió)
    val contkcal: Float = 0f,
    val contprote: Float = 0f,
    val contgrasas: Float = 0f,
    val contcarbs: Float = 0f
)
