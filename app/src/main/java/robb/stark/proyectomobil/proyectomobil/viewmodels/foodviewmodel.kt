package robb.stark.proyectomobil.proyectomobil.viewmodels

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import robb.stark.proyectomobil.proyectomobil.models.food
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.Preferencias
import robb.stark.proyectomobil.proyectomobil.models.Preferencias.Companion.CONTCARBS
import robb.stark.proyectomobil.proyectomobil.models.Preferencias.Companion.CONTGRASAS
import robb.stark.proyectomobil.proyectomobil.models.Preferencias.Companion.CONTKCAL
import robb.stark.proyectomobil.proyectomobil.models.Preferencias.Companion.CONTPROTE
import robb.stark.proyectomobil.proyectomobil.models.Preferencias.Companion.dataStore

@Composable
fun FoodCard(foodItem: food, navController: NavController) {

    val context = LocalContext.current
    val prefs = Preferencias(context)
    val coroutine = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEBE4FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(painter = painterResource(id = foodItem.img),
                contentDescription = "",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit)
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = foodItem.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = foodItem.descripcion ?: "Sin descripción",
                    fontSize = 14.sp
                )

                Text(
                    text = "KCal: ${foodItem.cals} kcal, Proteínas: ${foodItem.protein} g, Carbs: ${foodItem.carbs} g, Grasas: ${foodItem.fat} g ",
                    fontSize = 12.sp
                )
                Button(
                    onClick = {
                        coroutine.launch {
                            val preferencias = context.dataStore.data.first()

                            val currentKcal = preferencias[CONTKCAL] ?: 0f
                            val currentProtein = preferencias[CONTPROTE] ?: 0f
                            val currentFat = preferencias[CONTGRASAS] ?: 0f
                            val currentCarbs = preferencias[CONTCARBS] ?: 0f

                            val updatedKcal = currentKcal + foodItem.cals
                            val updatedProtein = currentProtein + foodItem.protein
                            val updatedFat = currentFat + foodItem.fat
                            val updatedCarbs = currentCarbs + foodItem.carbs

                            prefs.refreshProgress(
                                contkcal = updatedKcal.toDouble(),
                                contprote = updatedProtein,
                                contgrasas = updatedFat,
                                contcarbs = updatedCarbs.toDouble()
                            )
                        }
                        navController.navigate("home")
                    }
                    ,
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF41337A)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.add_food),
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}
