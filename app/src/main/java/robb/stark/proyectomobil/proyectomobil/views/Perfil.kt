package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.data.AuthRepository
import robb.stark.proyectomobil.proyectomobil.data.UserRepository
import robb.stark.proyectomobil.proyectomobil.models.UserProfile

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(navController: NavController) {

    val authRepository = remember { AuthRepository() }
    val userRepository = remember { UserRepository() }
    val uid = authRepository.currentUid
    val coroutine = rememberCoroutineScope()
    var showEditUsername by remember { mutableStateOf(false) }
    var showEditPersonalInfo by remember { mutableStateOf(false) }

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
    val savedAge = profile.age
    val savedHeight = profile.height
    val savedWeight = profile.weight
    val savedActivity = profile.activity.ifBlank { "---" }
    val savedGoal = profile.goal.ifBlank { "---" }

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { AppBottomBar(navController, currentRoute = "perfil") }
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
                    AppTopBar(navController, title = stringResource(id = R.string.profile))

                    Spacer(Modifier.height(20.dp))
                    Box(
                        Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(AppColors.Violeta.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.usercaract),
                            contentDescription = stringResource(id = R.string.profile_background),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }

                    Spacer(Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = savedUsername.ifBlank { "Sin nombre" },
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppColors.Morado,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar nombre",
                            tint = AppColors.TextoSecundario,
                            modifier = Modifier
                                .size(18.dp)
                                .clickable { showEditUsername = true }
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.member),
                        fontSize = 15.sp,
                        color = AppColors.TextoSecundario,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(20.dp))

                    Spacer(Modifier.height(16.dp))
                    AppCard {
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Your data",
                                fontSize = 14.sp,
                                color = AppColors.TextoSecundario
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { showEditPersonalInfo = true }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Change your personal data",
                                    tint = AppColors.Violeta,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    text = "Change",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppColors.Violeta
                                )
                            }
                        }
                        Spacer(Modifier.height(4.dp))
                        ProfileStatRow(
                            icon = Icons.Default.Cake,
                            label = stringResource(id = R.string.age),
                            value = "$savedAge"
                        )
                        ProfileStatRow(
                            icon = Icons.Default.Height,
                            label = stringResource(id = R.string.height),
                            value = "$savedHeight cm"
                        )
                        ProfileStatRow(
                            icon = Icons.Default.MonitorWeight,
                            label = stringResource(id = R.string.weight),
                            value = "$savedWeight kg"
                        )
                        ProfileStatRow(
                            icon = Icons.Default.DirectionsRun,
                            label = stringResource(id = R.string.activity_level),
                            value = savedActivity
                        )
                        ProfileStatRow(
                            icon = Icons.Default.Flag,
                            label = stringResource(id = R.string.goal),
                            value = savedGoal,
                            showDivider = false
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { navController.navigate("Rutina") },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AppColors.Violeta)
                        ) {
                            Text(
                                text = stringResource(id = R.string.go_to_workout),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Button(
                            onClick = { navController.navigate("plan") },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.Violeta,
                                contentColor = Color.White
                            )
                        ) {
                            Text(
                                text = stringResource(id = R.string.view_diet_plan),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }

    if (showEditUsername) {
        EditUsernameDialog(
            initialValue = savedUsername,
            onDismiss = { showEditUsername = false },
            onConfirm = { newUsername ->
                coroutine.launch {
                    userRepository.updateUsername(uid, newUsername)
                }
                showEditUsername = false
            }
        )
    }

    if (showEditPersonalInfo) {
        EditPersonalInfoDialog(
            initialAge = if (profile.age > 0) profile.age.toString() else "",
            initialHeight = if (profile.height > 0f) profile.height.toString() else "",
            initialWeight = if (profile.weight > 0f) profile.weight.toString() else "",
            initialActivity = profile.activity,
            initialGoal = profile.goal,
            onDismiss = { showEditPersonalInfo = false },
            onConfirm = { edad, estatura, peso, actividad, meta ->
                val tmb = (10 * peso) + (6.25 * estatura) - (5 * edad) + 5
                val actividadFactor = when (actividad) {
                    "Low" -> 1.2f
                    "Moderate" -> 1.55f
                    "High" -> 1.9f
                    else -> 1.2f
                }
                var calorias = tmb * actividadFactor
                calorias = when (meta) {
                    "Lose fat" -> calorias - 500
                    "Grow mass" -> calorias + 300
                    else -> calorias
                }
                val prote = peso * 2f
                val grasas = peso * 1f
                val kcalProte = prote * 4
                val kcalGrasas = grasas * 9
                val carbs = (calorias - kcalProte - kcalGrasas) / 4

                coroutine.launch {
                    userRepository.savePersonalData(
                        uid = uid,
                        age = edad,
                        height = estatura,
                        weight = peso,
                        activity = actividad,
                        goal = meta,
                        kcal = calorias.toFloat(),
                        prote = prote,
                        grasas = grasas,
                        carbs = carbs.toFloat()
                    )
                }
                showEditPersonalInfo = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditUsernameDialog(
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var value by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Change username") },
        text = {
            TextField(
                value = value,
                onValueChange = { value = it },
                singleLine = true,
                placeholder = { Text("Your name") },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = AppColors.InputBg,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            TextButton(
                onClick = { if (value.isNotBlank()) onConfirm(value.trim()) },
                enabled = value.isNotBlank()
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditPersonalInfoDialog(
    initialAge: String,
    initialHeight: String,
    initialWeight: String,
    initialActivity: String,
    initialGoal: String,
    onDismiss: () -> Unit,
    onConfirm: (age: Int, height: Float, weight: Float, activity: String, goal: String) -> Unit
) {
    var age by remember { mutableStateOf(initialAge) }
    var height by remember { mutableStateOf(initialHeight) }
    var weight by remember { mutableStateOf(initialWeight) }
    var activity by remember { mutableStateOf(initialActivity) }
    var goal by remember { mutableStateOf(initialGoal) }

    val isValid = age.toIntOrNull() != null && height.toFloatOrNull() != null && weight.toFloatOrNull() != null &&
            activity.isNotBlank() && goal.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit personal data") },
        text = {
            Column(
                Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                DialogTextField(age, { age = it }, "Age", KeyboardType.Number)
                Spacer(Modifier.height(10.dp))
                DialogTextField(height, { height = it }, "Height (cm)", KeyboardType.Decimal)
                Spacer(Modifier.height(10.dp))
                DialogTextField(weight, { weight = it }, "Weight (kg)", KeyboardType.Decimal)

                Spacer(Modifier.height(14.dp))
                Text("Level of activity", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Morado)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Low", "Moderate", "High").forEach { option ->
                        DialogChip(
                            text = option,
                            selected = activity == option,
                            modifier = Modifier.weight(1f),
                            onClick = { activity = option }
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))
                Text("Goal", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = AppColors.Morado)
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("Lose fat", "Grow mass").forEach { option ->
                        DialogChip(
                            text = option,
                            selected = goal == option,
                            modifier = Modifier.weight(1f),
                            onClick = { goal = option }
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = isValid,
                onClick = {
                    onConfirm(
                        age.toInt(),
                        height.toFloat(),
                        weight.toFloat(),
                        activity,
                        goal
                    )
                }
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Go back") }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = AppColors.InputBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun DialogChip(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) AppColors.Violeta else AppColors.ChipBg,
            contentColor = if (selected) Color.White else AppColors.ChipText
        ),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        Text(text, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun ProfileStatRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    showDivider: Boolean = true
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = AppColors.Violeta, modifier = Modifier.size(20.dp))
        Spacer(Modifier.width(10.dp))
        Text(text = label, fontSize = 16.sp, color = AppColors.Morado, modifier = Modifier.weight(1f))
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = AppColors.TextoSecundario)
    }
    if (showDivider) {
        HorizontalDivider(color = Color(0xFFEDEAF2))
    }
}