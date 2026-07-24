package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.data.AuthRepository
import robb.stark.proyectomobil.proyectomobil.data.UserRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculadora(navController: NavController) {

    val authRepository = remember { AuthRepository() }
    val userRepository = remember { UserRepository() }
    val uid = authRepository.currentUid
    val coroutine = rememberCoroutineScope()

    var age by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }

    var selectedActivity by remember { mutableStateOf("") }
    var selectedGoal by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    // Precarga los datos ya guardados en Firestore, si existen.
    LaunchedEffect(uid) {
        if (uid != null) {
            val profile = userRepository.observeUserProfile(uid).first()
            if (profile != null) {
                if (profile.age > 0) age = profile.age.toString()
                if (profile.height > 0f) height = profile.height.toString()
                if (profile.weight > 0f) weight = profile.weight.toString()
                if (profile.activity.isNotBlank()) selectedActivity = profile.activity
                if (profile.goal.isNotBlank()) selectedGoal = profile.goal
            }
        }
    }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { AppBottomBar(navController, currentRoute = "calculadora") }
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
                    AppTopBar(navController, title = stringResource(id = R.string.calculator))

                    Spacer(Modifier.height(20.dp))
                    Text(
                        text = stringResource(id = R.string.enter_personal_info),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.Morado
                    )

                    Spacer(Modifier.height(16.dp))
                    AppCard {
                        CalcTextField(
                            value = age,
                            onValueChange = { age = it },
                            placeholder = stringResource(id = R.string.age_placeholder),
                            keyboardType = KeyboardType.Number
                        )
                        Spacer(Modifier.height(12.dp))
                        CalcTextField(
                            value = height,
                            onValueChange = { height = it },
                            placeholder = stringResource(id = R.string.height_placeholder),
                            keyboardType = KeyboardType.Decimal
                        )
                        Spacer(Modifier.height(12.dp))
                        CalcTextField(
                            value = weight,
                            onValueChange = { weight = it },
                            placeholder = stringResource(id = R.string.weight_placeholder),
                            keyboardType = KeyboardType.Decimal
                        )
                    }

                    Spacer(Modifier.height(20.dp))
                    AppCard {
                        Text(
                            text = stringResource(id = R.string.activity_level),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColors.Morado
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(
                                stringResource(id = R.string.low),
                                stringResource(id = R.string.moderate),
                                stringResource(id = R.string.high)
                            ).forEach { option ->
                                SelectableChip(
                                    text = option,
                                    selected = selectedActivity == option,
                                    modifier = Modifier.weight(1f),
                                    onClick = { selectedActivity = option }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(20.dp))
                    AppCard {
                        Text(
                            text = stringResource(id = R.string.goal),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppColors.Morado
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(
                                stringResource(id = R.string.lose_fat),
                                stringResource(id = R.string.gain_mass)
                            ).forEach { option ->
                                SelectableChip(
                                    text = option,
                                    selected = selectedGoal == option,
                                    modifier = Modifier.weight(1f),
                                    onClick = { selectedGoal = option }
                                )
                            }
                        }
                    }

                    if (errorMessage.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = errorMessage,
                            color = Color(0xFFD32F2F),
                            fontSize = 14.sp
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = {
                            if (uid == null) {
                                errorMessage = "No hay sesión activa"
                                return@Button
                            }

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
                            val prote = peso * 2f
                            val grasas = peso * 1f
                            val kcalProte = prote * 4
                            val kcalGrasas = grasas * 9
                            val carbs = (calorias - kcalProte - kcalGrasas) / 4

                            isSaving = true
                            coroutine.launch {
                                val result = userRepository.savePersonalData(
                                    uid = uid,
                                    age = edad,
                                    height = estatura,
                                    weight = peso,
                                    activity = selectedActivity,
                                    goal = selectedGoal,
                                    kcal = calorias.toFloat(),
                                    prote = prote,
                                    grasas = grasas,
                                    carbs = carbs.toFloat()
                                )
                                isSaving = false
                                result.onSuccess {
                                    navController.navigate("home")
                                }.onFailure { e ->
                                    errorMessage = e.localizedMessage ?: "No se pudo guardar la información"
                                }
                            }
                        },
                        enabled = !isSaving,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Violeta,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        if (isSaving) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                        } else {
                            Text(
                                stringResource(id = R.string.calculate_save),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CalcTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = AppColors.InputBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(14.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SelectableChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) AppColors.Violeta else AppColors.ChipBg,
            contentColor = if (selected) Color.White else AppColors.ChipText
        ),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        Text(text, fontSize = 15.sp, fontWeight = FontWeight.Medium)
    }
}