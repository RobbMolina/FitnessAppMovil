package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.Preferencias

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val preferencias = Preferencias(context)
    val savedUsername by preferencias.username.collectAsState(initial = "")
    val savedPassword by preferencias.password.collectAsState(initial = "")

    val prefs = Preferencias(context)

    val Morado = Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val GrisClaro = Color(0xF2807F7F)
    val Grisfondo = Color(0x9CE7E9EC)

    val prote by prefs.prote.collectAsState(initial = 0f)
    val grasas by prefs.grasas.collectAsState(initial = 0f)
    val carbs by prefs.carbs.collectAsState(initial = 0f)
    val kcal by prefs.kcal.collectAsState(initial = 0f)

    val contkcals by preferencias.contkcal.collectAsState(initial = 0f)
    val contprote by preferencias.contprote.collectAsState(initial = 0f)
    val contgrasas by preferencias.contgrasas.collectAsState(initial = 0f)
    val contcarbs by preferencias.contcarbs.collectAsState(initial = 0f)

    Box(Modifier.fillMaxSize().padding(15.dp)) {

        Row(
            Modifier.fillMaxWidth().align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { navController.navigate("perfil") }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.profile),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            Text(text = stringResource(id = R.string.profile), fontSize = 20.sp)
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = stringResource(id = R.string.notifications),
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f)
            )
        }

        Column(Modifier.align(Alignment.Center).height(700.dp)) {
            Column {
                Text(
                    text = stringResource(id = R.string.welcome_back, savedUsername),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Violeta,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CircularProgressIndicator(
                    strokeWidth = 16.dp,
                    progress = (contkcals / kcal).coerceIn(0f, 1f),
                    color = Violeta,
                    trackColor = Color.LightGray,
                    modifier = Modifier.size(150.dp)
                )

                Text(
                    text = "${contkcals.toInt()}/${kcal.toInt()} ${stringResource(id = R.string.kcal_progress)}",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(20.dp)
                )

                Column(
                    Modifier.height(200.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = stringResource(id = R.string.protein), fontSize = 20.sp)
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            modifier = Modifier
                                .width(250.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.LightGray)
                        ) {
                            LinearProgressIndicator(
                                progress = (contprote / prote).coerceIn(0f, 1f),
                                color = Violeta,
                                trackColor = Color.Transparent,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Text(text = "${contprote.toInt()}/${prote.toInt()} g", fontSize = 20.sp)
                    }

                    Text(text = stringResource(id = R.string.carbs), fontSize = 20.sp)
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            modifier = Modifier
                                .width(250.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.LightGray)
                        ) {
                            LinearProgressIndicator(
                                progress = (contcarbs / carbs).coerceIn(0f, 1f),
                                color = Violeta,
                                trackColor = Color.Transparent,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Text(text = "${contcarbs.toInt()}/${carbs.toInt()} g", fontSize = 20.sp)
                    }

                    Text(text = stringResource(id = R.string.fats), fontSize = 20.sp)
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            modifier = Modifier
                                .width(250.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(50))
                                .background(Color.LightGray)
                        ) {
                            LinearProgressIndicator(
                                progress = (contgrasas / grasas).coerceIn(0f, 1f),
                                color = Violeta,
                                trackColor = Color.Transparent,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        Text(text = "${contgrasas.toInt()}/${grasas.toInt()} g", fontSize = 20.sp)
                    }
                }

                Column {

                    FloatingActionButton(
                        onClick = { navController.navigate("plan") },
                        modifier = Modifier.fillMaxWidth().padding(10.dp)
                    ) {
                        Text(text = stringResource(id = R.string.register_food), fontSize = 20.sp)
                    }
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(16.dp))
                .background(Grisfondo)
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { System.exit(0)}) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = stringResource(id = R.string.exit_text),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            IconButton(onClick = { navController.navigate("calculadora") }) {
                Icon(
                    imageVector = Icons.Default.Calculate,
                    contentDescription = stringResource(id = R.string.calculator),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(id = R.string.home),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            IconButton(onClick = { navController.navigate("plan") }) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = stringResource(id = R.string.diet),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            IconButton(onClick = { navController.navigate("Rutina") }) {
                Icon(
                    imageVector = Icons.Default.DirectionsRun,
                    contentDescription = stringResource(id = R.string.workout),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}
