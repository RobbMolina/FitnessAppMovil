package robb.stark.proyectomobil.proyectomobil.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.suspendCancellableCoroutine
import robb.stark.proyectomobil.proyectomobil.models.UserProfile
import kotlin.coroutines.resume

/**
 * Encapsula todas las llamadas a Cloud Firestore para el documento
 * users/{uid}. Nada fuera de esta clase toca FirebaseFirestore directamente.
 */
class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private fun userDoc(uid: String) = db.collection("users").document(uid)

    /** Crea el documento inicial justo después de registrarse. */
    suspend fun createUserProfile(uid: String, username: String, email: String): Result<Unit> =
        suspendCancellableCoroutine { cont ->
            val profile = UserProfile(uid = uid, username = username, email = email)
            userDoc(uid).set(profile)
                .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                .addOnFailureListener { e -> cont.resume(Result.failure(e)) }
        }

    /**
     * Observa el perfil en tiempo real (se usa en Home/Perfil con collectAsState).
     * Si Firestore falla (p. ej. PERMISSION_DENIED por reglas de seguridad, o sin
     * conexión), el error se atrapa con [catch] y se emite null en vez de crashear
     * la app entera.
     */
    fun observeUserProfile(uid: String): Flow<UserProfile?> = callbackFlow {
        val listener = userDoc(uid).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            trySend(snapshot?.toObject(UserProfile::class.java))
        }
        awaitClose { listener.remove() }
    }.catch { e ->
        Log.e("UserRepository", "Error observando el perfil de $uid", e)
        emit(null)
    }

    /** Guarda edad, altura, peso, actividad, meta y metas de macros (pantalla Calculadora). */
    suspend fun savePersonalData(
        uid: String,
        age: Int,
        height: Float,
        weight: Float,
        activity: String,
        goal: String,
        kcal: Float,
        prote: Float,
        grasas: Float,
        carbs: Float
    ): Result<Unit> = suspendCancellableCoroutine { cont ->
        val updates = mapOf(
            "age" to age,
            "height" to height,
            "weight" to weight,
            "activity" to activity,
            "goal" to goal,
            "kcal" to kcal,
            "prote" to prote,
            "grasas" to grasas,
            "carbs" to carbs
        )
        // set(..., merge = true) en vez de update(): si el documento ya existe, solo
        // actualiza estos campos; si no existe (p. ej. porque createUserProfile falló
        // en su momento), lo crea con estos campos en vez de lanzar NOT_FOUND.
        userDoc(uid).set(updates, SetOptions.merge())
            .addOnSuccessListener { cont.resume(Result.success(Unit)) }
            .addOnFailureListener { e -> cont.resume(Result.failure(e)) }
    }

    /** Actualiza el progreso del día (cuando el usuario agrega un alimento en Plan). */
    suspend fun updateProgress(
        uid: String,
        contkcal: Float,
        contprote: Float,
        contgrasas: Float,
        contcarbs: Float
    ): Result<Unit> = suspendCancellableCoroutine { cont ->
        val updates = mapOf(
            "contkcal" to contkcal,
            "contprote" to contprote,
            "contgrasas" to contgrasas,
            "contcarbs" to contcarbs
        )
        userDoc(uid).set(updates, SetOptions.merge())
            .addOnSuccessListener { cont.resume(Result.success(Unit)) }
            .addOnFailureListener { e -> cont.resume(Result.failure(e)) }
    }

    /**
     * Guarda o corrige el nombre de usuario. Útil para cuentas cuyo documento
     * no se creó correctamente al registrarse (p. ej. por un error de permisos)
     * y necesitan volver a establecer su username desde Perfil.
     */
    suspend fun updateUsername(uid: String, username: String): Result<Unit> =
        suspendCancellableCoroutine { cont ->
            userDoc(uid).set(mapOf("username" to username), SetOptions.merge())
                .addOnSuccessListener { cont.resume(Result.success(Unit)) }
                .addOnFailureListener { e -> cont.resume(Result.failure(e)) }
        }
}