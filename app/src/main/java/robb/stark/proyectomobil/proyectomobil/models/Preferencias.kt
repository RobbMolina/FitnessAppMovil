package robb.stark.proyectomobil.proyectomobil.models

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class Preferencias(private val context: Context) {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

        val AGE = intPreferencesKey("age")
        val HEIGHT = floatPreferencesKey("height")
        val WEIGHT = floatPreferencesKey("weight")
        val ACTIVITY = stringPreferencesKey("activity_level")
        val GOAL = stringPreferencesKey("goal")
        val SIGNUP = booleanPreferencesKey("signed_up")

        val KCAL = floatPreferencesKey("kcal")
        val PROTE = floatPreferencesKey("prote")
        val GRASAS = floatPreferencesKey("grasas")
        val CARBS = floatPreferencesKey("carbs")

        val CONTKCAL = floatPreferencesKey("contkcal")
        val CONTPROTE = floatPreferencesKey("contprote")
        val CONTGRASAS = floatPreferencesKey("contprote")
        val CONTCARBS = floatPreferencesKey("contcarbs")

        val USERNAME = stringPreferencesKey("username")
        val PASSWORD = stringPreferencesKey("password")
    }

    val age: Flow<Int> = context.dataStore.data.map { preferences -> preferences[AGE] ?: 0 }
    val height: Flow<Float> = context.dataStore.data.map { preferences -> preferences[HEIGHT] ?: 0f }
    val weight: Flow<Float> = context.dataStore.data.map { preferences -> preferences[WEIGHT] ?: 0f }
    val activity: Flow<String> = context.dataStore.data.map { preferences -> preferences[ACTIVITY] ?: "" }
    val goal: Flow<String> = context.dataStore.data.map { preferences -> preferences[GOAL] ?: "" }
    val isSignedUp: Flow<Boolean> = context.dataStore.data.map { preferences -> preferences[SIGNUP] ?: false }
    val kcal: Flow<Float> = context.dataStore.data.map { preferences -> preferences[KCAL] ?: 0f }
    val prote: Flow<Float> = context.dataStore.data.map { preferences -> preferences[PROTE] ?: 0f }
    val grasas: Flow<Float> = context.dataStore.data.map { preferences -> preferences[GRASAS] ?: 0f }
    val carbs: Flow<Float> = context.dataStore.data.map { preferences -> preferences[CARBS] ?: 0f }

    val contkcal: Flow<Float> = context.dataStore.data.map { preferences -> preferences[CONTKCAL] ?: 0f }
    val contprote: Flow<Float> = context.dataStore.data.map { preferences -> preferences[CONTPROTE] ?: 0f }
    val contgrasas: Flow<Float> = context.dataStore.data.map { preferences -> preferences[CONTGRASAS] ?: 0f }
    val contcarbs: Flow<Float> = context.dataStore.data.map { preferences -> preferences[CONTCARBS] ?: 0f }


    val username: Flow<String> = context.dataStore.data.map { preferences -> preferences[USERNAME] ?: "" }
    val password: Flow<String> = context.dataStore.data.map { preferences -> preferences[PASSWORD] ?: "" }

    suspend fun getAge(): Int {
        val pref = context.dataStore.data.first()
        return pref[AGE] ?: 0
    }

    suspend fun saveUserData(username: String, password: String) {
        context.dataStore.edit { settings ->
            settings[USERNAME] = username
            settings[PASSWORD] = password
        }
    }

    suspend fun savePersonData(
        personAge: Int,
        personHeight: Float,
        personWeight: Float,
        personActivity: String,
        personGoal: String,
        kcal: Double,
        prote: Float,
        grasas: Float,
        carbs: Double
    ) {
        context.dataStore.edit { settings ->
            settings[AGE] = personAge
            settings[HEIGHT] = personHeight
            settings[WEIGHT] = personWeight
            settings[ACTIVITY] = personActivity
            settings[GOAL] = personGoal
            settings[KCAL] = kcal.toFloat()
            settings[PROTE] = prote
            settings[GRASAS] = grasas
            settings[CARBS] = carbs.toFloat()
        }
    }

    suspend fun refreshProgress(
        contkcal: Double,
        contprote: Float,
        contgrasas: Float,
        contcarbs: Double
    ) {
        context.dataStore.edit { settings ->
            settings[CONTKCAL] = contkcal.toFloat()
            settings[CONTPROTE] = contprote
            settings[CONTGRASAS] = contgrasas
            settings[CONTCARBS] = contcarbs.toFloat()
        }
    }


    suspend fun clearPersonData() {
        context.dataStore.edit { settings ->
            settings.clear()
        }
    }


}
