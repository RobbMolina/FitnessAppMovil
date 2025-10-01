package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.viewmodels.EjercicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true)
@Composable
fun Rutina(navController: NavController, viewModel: EjercicioViewModel) {
    val Morado = Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val Grisfondo = Color(0x9CE7E9EC)


    Box(modifier = Modifier.fillMaxSize().padding(15.dp)) {
        Row(Modifier.fillMaxWidth().align(Alignment.TopCenter), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {navController.navigate("perfil")}) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "icono",
                    tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f)
                )
            }
            Text( text = "Perfil", fontSize = 20.sp)
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "icono",
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f))

        }

            Column(Modifier.fillMaxWidth().align(Alignment.Center)) {
                LazyColumn(Modifier.fillMaxWidth().height(650.dp)) {
                    item {
                        Button(onClick = {
                            viewModel.traerEjercicios()
                        }) {
                            Text(text = "Cargar ejercicios")
                        }
                    }
                    items(viewModel.ejercicios) { ejercicio ->
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFDDDAF3))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = ejercicio.name,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                                Text(
                                    text = "Grupo muscular: ${ejercicio.bodyPart}",
                                    color = Color.DarkGray
                                )
                                Text(text = "Equipo: ${ejercicio.equipment}", color = Color.Gray)
                                AsyncImage(
                                    model = ejercicio.gifUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }

                }
            }

        Row(
            Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Grisfondo)
                .padding(15.dp).align(Alignment.BottomCenter), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { System.exit(0)}) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "salir", tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f))
            }
            IconButton(onClick = { navController.navigate("calculadora")}) {
                Icon(imageVector = Icons.Default.Calculate, contentDescription = "calculadora de macros", tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f))
            }
            IconButton(onClick = { navController.navigate("home")}) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "home", tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f))
            }
            IconButton(onClick = { navController.navigate("plan") }) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = "dieta",
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            IconButton(onClick = { navController.navigate("Rutina")}) {
                Icon(imageVector = Icons.Default.DirectionsRun, contentDescription = "dieta", tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f))
            }
        }
    }
}
