package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.Preferencias
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculadora(navController: NavController){

    val Morado= Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val GrisClaro = Color(0xF2807F7F)
    val Grisfondo = Color(0x9CE7E9EC)

    val context = LocalContext.current
    val preferencias = Preferencias(context)
    val coroutine = rememberCoroutineScope()

    val savedAge by preferencias.age.collectAsState(initial = 0)
    val savedHeight by preferencias.height.collectAsState(initial = 0f)
    val savedWeight by preferencias.weight.collectAsState(initial = 0f)
    val savedActivity by preferencias.activity.collectAsState(initial = "---")
    val savedGoal by preferencias.goal.collectAsState(initial = "---")

    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var selectedActivity by remember { mutableStateOf("") }
    var selectedGoal by remember { mutableStateOf("") }

    Box(
        Modifier
            .fillMaxSize()
            .padding(15.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {navController.navigate("perfil")}) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = stringResource(id = R.string.profile),
                    tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f)
                )
            }
            Text( text = stringResource(id = R.string.profile), fontSize = 20.sp)
            Icon(imageVector = Icons.Default.Notifications, contentDescription = stringResource(id = R.string.notifications),
                tint = Violeta,
                modifier = Modifier
                    .size(35.dp)
                    .scale(-1f, 1f))

        }

        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 80.dp)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = stringResource(id = R.string.enter_personal_info), fontSize = 20.sp)
            Spacer(Modifier.height(5.dp))
            TextField(
                value = age,
                onValueChange = { age = it },
                placeholder = { Text(stringResource(id = R.string.age_placeholder)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xDAE1D4EA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.height(5.dp))

            TextField(
                value = height,
                onValueChange = { height = it },
                placeholder = { Text(stringResource(id = R.string.height_placeholder)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xDAE1D4EA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.height(5.dp))
            TextField(
                value = weight,
                onValueChange = { weight = it },
                placeholder = { Text(stringResource(id = R.string.weight_placeholder)) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xDAE1D4EA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.activity_level), fontSize = 20.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(stringResource(id = R.string.low), stringResource(id = R.string.moderate), stringResource(id = R.string.high)).forEach { option ->
                    Button(
                        onClick = { selectedActivity = option },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedActivity == option) Violeta else Color(0xDAE1D4EA),
                            contentColor = if (selectedActivity == option) Color.White else Color(0xFF857A9B)
                        )
                    ) {
                        Text(option, fontSize = 20.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.goal), fontSize = 20.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(stringResource(id = R.string.lose_fat), stringResource(id = R.string.gain_mass)).forEach { option ->
                    Button(
                        onClick = { selectedGoal = option },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedGoal == option) Violeta else Color(0xDAE1D4EA),
                            contentColor = if (selectedGoal == option) Color.White else Color(0xFF857A9B)
                        )
                    ) {
                        Text(option, fontSize = 20.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
            Button(colors = ButtonDefaults.buttonColors(containerColor = Violeta, contentColor = Color.White),
                onClick = {
                    coroutine.launch {
                        val edad = age.toIntOrNull() ?: 0
                        val estatura = height.toFloatOrNull() ?: 0f
                        val peso = weight.toFloatOrNull() ?: 0f
                        var tmb = (10 * peso) + (6.25 * estatura) - (5 * edad) + 5

                        val actividadFactor = when (selectedActivity) {
                            "Bajo" -> 1.2f
                            "Moderado" -> 1.55f
                            "Alto" -> 1.9f
                            else -> 1.2f
                        }

                        var calorias = tmb * actividadFactor
                        calorias = when (selectedGoal) {
                            "Perder grasa" -> calorias - 500
                            "Aumentar masa" -> calorias + 300
                            else -> calorias
                        }
                        val prote = peso * 2f                       // g
                        val grasas = peso * 1f                      // g
                        val kcalProte = prote * 4                   // kcal
                        val kcalGrasas = grasas * 9                 // kcal
                        val carbs = (calorias - kcalProte - kcalGrasas) / 4

                        preferencias.savePersonData(
                            personAge = edad,
                            personHeight = estatura,
                            personWeight = peso,
                            personActivity = selectedActivity,
                            personGoal = selectedGoal,
                            kcal = calorias,
                            prote = prote,
                            grasas = grasas,
                            carbs = carbs
                        )
                    }
                    navController.navigate("home")
                }) {
                Text(stringResource(id = R.string.calculate_save), fontSize = 20.sp)
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(16.dp))
                .background(Grisfondo)
                .padding(15.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { System.exit(0)}) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = stringResource(id = R.string.exit_button), tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = { navController.navigate("calculadora")}) {
                Icon(imageVector = Icons.Default.Calculate, contentDescription = stringResource(id = R.string.calculator), tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = { navController.navigate("home")}) {
                Icon(imageVector = Icons.Default.Home, contentDescription = stringResource(id = R.string.home), tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = { navController.navigate("plan") }) {
                Icon(
                    imageVector = Icons.Default.Fastfood,
                    contentDescription = stringResource(id = R.string.diet),
                    tint = Violeta,
                    modifier = Modifier.size(35.dp).scale(-1f, 1f)
                )
            }
            IconButton(onClick = { navController.navigate("Rutina")}) {
                Icon(imageVector = Icons.Default.DirectionsRun, contentDescription = stringResource(id = R.string.workout), tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
        }
    }
}
