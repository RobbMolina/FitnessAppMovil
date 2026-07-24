package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.data.AuthRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navegar: NavController) {

    val authRepository = remember { AuthRepository() }
    val coroutineScope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    AppBackground {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier.fillMaxWidth().weight(2.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logov2),
                    contentDescription = stringResource(id = R.string.profile),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(140.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Violeta
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(id = R.string.app_subtitle),
                    fontSize = 15.sp,
                    color = Color(0xFF8A8A99)
                )
            }

            AppCard(padding = 22.dp) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text(stringResource(id = R.string.email_placeholder)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = stringResource(id = R.string.profile),
                            tint = AppColors.Violeta
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = AppColors.InputBg,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(12.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text(stringResource(id = R.string.password_placeholder)) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(id = R.string.password_placeholder),
                            tint = AppColors.Violeta
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = AppColors.InputBg,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(14.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (passwordVisible)
                        androidx.compose.ui.text.input.VisualTransformation.None
                    else PasswordVisualTransformation(),
                    trailingIcon = {
                        TextButton(onClick = { passwordVisible = !passwordVisible }) {
                            Text(text = if (passwordVisible) "Ocultar" else "Ver", fontSize = 12.sp)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = Color(0xFFD32F2F),
                        fontSize = 14.sp
                    )
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Type your email and password"
                        } else {
                            errorMessage = ""
                            isLoading = true
                            coroutineScope.launch {
                                val result = authRepository.login(email.trim(), password)
                                result.onSuccess {
                                    isLoading = false
                                    navegar.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }.onFailure { e ->
                                    isLoading = false
                                    errorMessage = e.localizedMessage ?: "Failed to log in"
                                }
                            }
                        }
                    },
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Violeta,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                    } else {
                        Text(
                            text = stringResource(id = R.string.login_button),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(Modifier.height(10.dp))
                Text(
                    text = stringResource(id = R.string.forgot_password),
                    fontSize = 13.sp,
                    color = Color(0xFF8A8A99),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navegar.navigate("recuperar") },
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            Column(
                Modifier.fillMaxWidth().weight(1.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(24.dp))

                val noAccountBase = stringResource(id = R.string.no_account)
                val noAccountLink = stringResource(id = R.string.no_account_link)
                val noAccountText = buildAnnotatedString {
                    append("$noAccountBase ")
                    withStyle(
                        style = SpanStyle(
                            color = AppColors.Violeta,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(noAccountLink)
                    }
                }

                Text(
                    text = noAccountText,
                    fontSize = 15.sp,
                    color = Color(0xFF6B6B75),
                    modifier = Modifier.clickable { navegar.navigate("registro") }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}