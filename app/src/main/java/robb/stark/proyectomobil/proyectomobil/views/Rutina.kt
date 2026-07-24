package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import robb.stark.proyectomobil.proyectomobil.models.Exercise
import robb.stark.proyectomobil.proyectomobil.viewmodels.DiaConEjercicios
import robb.stark.proyectomobil.proyectomobil.viewmodels.EjercicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Rutina(navController: NavController, viewModel: EjercicioViewModel) {

    // Día expandido actualmente (solo uno a la vez, estilo acordeón).
    var diaExpandido by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { AppBottomBar(navController, currentRoute = "Rutina") }
    ) { innerPadding ->
        AppBackground {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Spacer(Modifier.height(12.dp))
                AppTopBar(navController, title = "Routine")

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Your weekly routine",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Morado
                )
                Text(
                    text = "Muscle-group split, 5 workout days + 2 rest days",
                    fontSize = 13.sp,
                    color = AppColors.TextoSecundario
                )

                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { viewModel.traerEjercicios() },
                    enabled = !viewModel.isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Violeta,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                        Spacer(Modifier.width(8.dp))
                        Text("Loading...", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    } else {
                        Icon(imageVector = Icons.Default.FitnessCenter, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Load exercises", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(Modifier.height(16.dp))

                if (viewModel.ejercicios.isEmpty()) {
                    Box(Modifier.fillMaxWidth().padding(top = 40.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (viewModel.isLoading)
                                "Loading your weekly routine..."
                            else
                                "Press \"Load exercises\" to see your weekly routine",
                            fontSize = 15.sp,
                            color = AppColors.TextoSecundario,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 20.dp)
                    ) {
                        items(viewModel.rutinaSemanal) { diaConEjercicios ->
                            DiaCard(
                                dia = diaConEjercicios,
                                expandido = diaExpandido == diaConEjercicios.dia,
                                onToggle = {
                                    diaExpandido = if (diaExpandido == diaConEjercicios.dia) {
                                        null
                                    } else {
                                        diaConEjercicios.dia
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DiaCard(
    dia: DiaConEjercicios,
    expandido: Boolean,
    onToggle: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !dia.esDescanso) { onToggle() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            if (dia.esDescanso) Color(0xFFEDEAF2) else AppColors.Violeta.copy(alpha = 0.12f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (dia.esDescanso) Icons.Default.Bedtime else Icons.Default.FitnessCenter,
                        contentDescription = null,
                        tint = if (dia.esDescanso) AppColors.TextoSecundario else AppColors.Violeta,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Text(
                        text = dia.dia,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Morado
                    )
                    Text(
                        text = dia.grupoMuscular,
                        fontSize = 14.sp,
                        color = AppColors.TextoSecundario
                    )
                }

                if (!dia.esDescanso) {
                    Icon(
                        imageVector = if (expandido) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = AppColors.TextoSecundario
                    )
                }
            }

            if (!dia.esDescanso) {
                AnimatedVisibility(
                    visible = expandido,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(Modifier.padding(top = 14.dp)) {
                        if (dia.ejercicios.isEmpty()) {
                            Text(
                                text = "No exercises found yet for this muscle group",
                                fontSize = 13.sp,
                                color = AppColors.TextoSecundario
                            )
                        } else {
                            dia.ejercicios.forEachIndexed { index, ejercicio ->
                                EjercicioMiniItem(ejercicio)
                                if (index != dia.ejercicios.lastIndex) {
                                    Spacer(Modifier.height(12.dp))
                                    HorizontalDivider(color = Color(0xFFEDEAF2))
                                    Spacer(Modifier.height(12.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EjercicioMiniItem(ejercicio: Exercise) {
    Column {
        Text(
            text = ejercicio.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppColors.Morado
        )
        Spacer(Modifier.height(2.dp))
        Text(
            text = "Body part: ${ejercicio.bodyPart}  •  Equipment: ${ejercicio.equipment}",
            fontSize = 13.sp,
            color = AppColors.TextoSecundario
        )
        Spacer(Modifier.height(8.dp))
        AsyncImage(
            model = ejercicio.gifUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(14.dp)),
            contentScale = ContentScale.Crop
        )
    }
}