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

class EjercicioViewModel : ViewModel() {
    var ejercicios by mutableStateOf<List<Exercise>>(emptyList())

    fun traerEjercicios() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ejercicios = API().getEjercicios()
            }
        }
    }
}
