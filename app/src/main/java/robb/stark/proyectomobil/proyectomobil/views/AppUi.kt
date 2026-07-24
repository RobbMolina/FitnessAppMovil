package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import robb.stark.proyectomobil.R

/**
 * Paleta de colores compartida por toda la app.
 * Cualquier pantalla nueva debe usar estos valores en vez de declarar los suyos,
 * para mantener consistencia visual.
 */
object AppColors {
    val Morado = Color(0xFF2D1B36)
    val Violeta = Color(0xFF4C3A9E)
    val VioletaClaro = Color(0xFF7B6BDB)
    val Azul = Color(0xFF6EA4BF)
    val FondoTop = Color(0xFFF4F1FB)
    val FondoBottom = Color(0xFFE7E9F5)
    val CardBg = Color(0xFFFFFFFF)
    val TextoSecundario = Color(0xFF8B8798)
    val InputBg = Color(0xFFEFEBFA)
    val ChipBg = Color(0xFFEFEBFA)
    val ChipText = Color(0xFF857A9B)
}

/** Fondo degradado estándar usado en todas las pantallas principales. */
@Composable
fun AppBackground(content: @Composable BoxScope.() -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(AppColors.FondoTop, AppColors.FondoBottom))),
        content = content
    )
}

/** Barra superior estándar: solo el título, centrado. */
@Composable
fun AppTopBar(navController: NavController, title: String) {
    Box(
        Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = AppColors.Morado
        )
    }
}

/**
 * Barra de navegación inferior estándar.
 * [currentRoute] resalta el ícono correspondiente a la pantalla activa.
 */
@Composable
fun AppBottomBar(navController: NavController, currentRoute: String) {
    NavigationBar(
        containerColor = AppColors.CardBg,
        modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
    ) {
        val colors = NavigationBarItemDefaults.colors(
            selectedIconColor = AppColors.Violeta,
            unselectedIconColor = AppColors.Violeta,
            indicatorColor = Color(0xFFEDEAF7)
        )

        NavigationBarItem(
            selected = false,
            onClick = { System.exit(0) },
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = stringResource(id = R.string.exit_text)) },
            colors = colors
        )
        NavigationBarItem(
            selected = currentRoute == "perfil",
            onClick = { navController.navigate("perfil") },
            icon = { Icon(Icons.Default.Person, contentDescription = stringResource(id = R.string.profile)) },
            colors = colors
        )
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(id = R.string.home)) },
            colors = colors
        )
        NavigationBarItem(
            selected = currentRoute == "plan",
            onClick = { navController.navigate("plan") },
            icon = { Icon(Icons.Default.Fastfood, contentDescription = stringResource(id = R.string.diet)) },
            colors = colors
        )
        NavigationBarItem(
            selected = currentRoute == "Rutina",
            onClick = { navController.navigate("Rutina") },
            icon = { Icon(Icons.Default.DirectionsRun, contentDescription = stringResource(id = R.string.workout)) },
            colors = colors
        )
    }
}

/** Tarjeta contenedora estándar (fondo blanco, esquinas redondeadas, sombra suave). */
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    padding: Dp = 18.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.CardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(Modifier.padding(padding), content = content)
    }
}