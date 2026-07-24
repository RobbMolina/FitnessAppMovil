package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.data.AuthRepository
import robb.stark.proyectomobil.proyectomobil.data.UserRepository
import robb.stark.proyectomobil.proyectomobil.models.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    val authRepository = remember { AuthRepository() }
    val userRepository = remember { UserRepository() }
    val uid = authRepository.currentUid

    // Si no hay sesión activa, regresa al login.
    LaunchedEffect(uid) {
        if (uid == null) {
            navController.navigate("login") {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    if (uid == null) return

    val userProfile by userRepository.observeUserProfile(uid).collectAsState(initial = null)
    val profile = userProfile ?: UserProfile()

    val savedUsername = profile.username

    val kcal = profile.kcal
    val prote = profile.prote
    val grasas = profile.grasas
    val carbs = profile.carbs

    val contkcals = profile.contkcal
    val contprote = profile.contprote
    val contgrasas = profile.contgrasas
    val contcarbs = profile.contcarbs

    val kcalProgress by animateFloatAsState(
        targetValue = (contkcals / kcal.coerceAtLeast(1f)).coerceIn(0f, 1f),
        animationSpec = tween(600), label = "kcalProgress"
    )

    // Nombres de los macros/calorías que ya se pasaron de la meta diaria.
    val exceededLabels = remember(contkcals, contprote, contgrasas, contcarbs, kcal, prote, grasas, carbs) {
        buildList {
            if (kcal > 0f && contkcals > kcal) add("Calories")
            if (prote > 0f && contprote > prote) add("Protein")
            if (carbs > 0f && contcarbs > carbs) add("Carbs")
            if (grasas > 0f && contgrasas > grasas) add("Fat")
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { AppBottomBar(navController, currentRoute = "home") }
    ) { innerPadding ->
        AppBackground {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp)
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(Modifier.height(12.dp))
                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.welcome_back, savedUsername),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Morado,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(24.dp))
                    AnimatedVisibility(
                        visible = exceededLabels.isNotEmpty(),
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Column {
                            ExceededMacrosBanner(exceededLabels = exceededLabels)
                            Spacer(Modifier.height(20.dp))
                        }
                    }

                    KcalCard(contkcals = contkcals, kcal = kcal, progress = kcalProgress)

                    Spacer(Modifier.height(20.dp))
                    MacroCard(
                        icon = Icons.Default.FitnessCenter,
                        label = stringResource(id = R.string.protein),
                        current = contprote,
                        total = prote,
                        color = AppColors.Violeta
                    )
                    Spacer(Modifier.height(12.dp))
                    MacroCard(
                        icon = Icons.Default.Grain,
                        label = stringResource(id = R.string.carbs),
                        current = contcarbs,
                        total = carbs,
                        color = AppColors.Azul
                    )
                    Spacer(Modifier.height(12.dp))
                    MacroCard(
                        icon = Icons.Default.WaterDrop,
                        label = stringResource(id = R.string.fats),
                        current = contgrasas,
                        total = grasas,
                        color = AppColors.VioletaClaro
                    )

                    Spacer(Modifier.height(28.dp))
                    ExtendedFloatingActionButton(
                        onClick = { navController.navigate("plan") },
                        containerColor = AppColors.Violeta,
                        contentColor = Color.White,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = stringResource(id = R.string.register_food),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
private fun ExceededMacrosBanner(exceededLabels: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFDECEA)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = Color(0xFFD32F2F),
                modifier = Modifier.size(22.dp)
            )
            Spacer(Modifier.width(10.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    text = "You surpassed your daily macros ",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFB71C1C)
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = exceededLabels.joinToString(", "),
                    fontSize = 13.sp,
                    color = Color(0xFFB71C1C)
                )
            }
        }
    }
}

@Composable
private fun KcalCard(contkcals: Float, kcal: Float, progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = 1f,
                    strokeWidth = 14.dp,
                    color = Color(0xFFEDEAF7),
                    modifier = Modifier.size(170.dp)
                )
                CircularProgressIndicator(
                    progress = progress,
                    strokeWidth = 14.dp,
                    color = AppColors.Violeta,
                    trackColor = Color.Transparent,
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier.size(170.dp)
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${contkcals.toInt()}",
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Morado
                    )
                    Text(
                        text = "/ ${kcal.toInt()} ${stringResource(id = R.string.kcal_progress)}",
                        fontSize = 14.sp,
                        color = AppColors.TextoSecundario
                    )
                }
            }
        }
    }
}

@Composable
private fun MacroCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    current: Float,
    total: Float,
    color: Color
) {
    val progress by animateFloatAsState(
        targetValue = (current / total.coerceAtLeast(1f)).coerceIn(0f, 1f),
        animationSpec = tween(600), label = "macroProgress"
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = color, modifier = Modifier.size(22.dp))
            }

            Spacer(Modifier.width(14.dp))

            Column(Modifier.weight(1f)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = label, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Morado)
                    Text(
                        text = "${current.toInt()}/${total.toInt()} g",
                        fontSize = 14.sp,
                        color = AppColors.TextoSecundario
                    )
                }
                Spacer(Modifier.height(8.dp))
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color(0xFFEDEAF2))
                ) {
                    LinearProgressIndicator(
                        progress = progress,
                        color = color,
                        trackColor = Color.Transparent,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(50))
                    )
                }
            }
        }
    }
}