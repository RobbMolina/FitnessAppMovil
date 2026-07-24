package robb.stark.proyectomobil.proyectomobil.viewmodels

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.data.AuthRepository
import robb.stark.proyectomobil.proyectomobil.data.UserRepository
import robb.stark.proyectomobil.proyectomobil.models.food
import robb.stark.proyectomobil.proyectomobil.views.AppColors

@Composable
fun FoodCard(foodItem: food, navController: NavController) {

    val authRepository = remember { AuthRepository() }
    val userRepository = remember { UserRepository() }
    val coroutine = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = foodItem.img),
                contentDescription = foodItem.nombre,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.InputBg),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = foodItem.nombre,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Morado
                )
                Text(
                    text = foodItem.descripcion ?: "Sin descripción",
                    fontSize = 13.sp,
                    color = AppColors.TextoSecundario,
                    maxLines = 2
                )

                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${foodItem.cals.toInt()} kcal  ·  P ${foodItem.protein.toInt()}g  ·  C ${foodItem.carbs.toInt()}g  ·  G ${foodItem.fat.toInt()}g",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppColors.Violeta
                )

                Spacer(Modifier.height(6.dp))
                Button(
                    onClick = {
                        val uid = authRepository.currentUid ?: return@Button

                        coroutine.launch {
                            val profile = userRepository.observeUserProfile(uid).first()

                            val currentKcal = profile?.contkcal ?: 0f
                            val currentProtein = profile?.contprote ?: 0f
                            val currentFat = profile?.contgrasas ?: 0f
                            val currentCarbs = profile?.contcarbs ?: 0f

                            val updatedKcal = currentKcal + foodItem.cals
                            val updatedProtein = currentProtein + foodItem.protein
                            val updatedFat = currentFat + foodItem.fat
                            val updatedCarbs = currentCarbs + foodItem.carbs

                            userRepository.updateProgress(
                                uid = uid,
                                contkcal = updatedKcal,
                                contprote = updatedProtein,
                                contgrasas = updatedFat,
                                contcarbs = updatedCarbs
                            )

                            navController.navigate("home")
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Violeta,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.add_food),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}