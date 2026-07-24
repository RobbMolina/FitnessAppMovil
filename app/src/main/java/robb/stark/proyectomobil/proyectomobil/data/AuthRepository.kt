package robb.stark.proyectomobil.proyectomobil.data

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

/**
 * Encapsula todas las llamadas a Firebase Authentication.
 * Las pantallas (Login, Registro) solo llaman a estas funciones,
 * nunca usan FirebaseAuth directamente.
 */
class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    /** uid del usuario logueado actualmente, o null si no hay sesión activa. */
    val currentUid: String?
        get() = auth.currentUser?.uid

    fun isLoggedIn(): Boolean = auth.currentUser != null

    suspend fun register(email: String, password: String): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid

            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception("Failed to create user"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid

            if (uid != null) {
                Result.success(uid)
            } else {
                Result.failure(Exception("Failed to log in"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun logout() {
        auth.signOut()
    }
}