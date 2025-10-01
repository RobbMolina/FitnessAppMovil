package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.Preferencias

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(navegar: NavController) {
    val Morado = Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val GrisClaro = Color(0xFF919191)

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") } // Error message

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val preferencias = Preferencias(context)

    Column(
        Modifier.fillMaxSize().background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier.fillMaxWidth().weight(2.5f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.fitnesslogo),
                contentDescription = stringResource(id = R.string.profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp)
            )
            Row {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Violeta
                )
            }
        }

        Column(
            Modifier.fillMaxWidth().weight(1.5f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = user,
                onValueChange = { user = it },
                placeholder = { Text(stringResource(id = R.string.email_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = stringResource(id = R.string.profile),
                        tint = Violeta
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xDAE1D4EA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(stringResource(id = R.string.password_placeholder)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = stringResource(id = R.string.password_placeholder),
                        tint = Violeta
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color(0xDAE1D4EA),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        Column(
            Modifier.fillMaxWidth().weight(1.5f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    if (user.isEmpty() || password.isEmpty()) {
                        //errorMessage = stringResource(id = R.string.login_error_message)
                    } else {
                        coroutineScope.launch {
                            preferencias.saveUserData(user, password)
                        }

                        errorMessage = ""
                        navegar.navigate("Home?user=$user&password=$password")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Violeta),
                modifier = Modifier.width(250.dp)
            ) {
                Text(text = stringResource(id = R.string.login_button), fontSize = 25.sp, modifier = Modifier.padding(vertical = 8.dp))
            }

            Text(text = stringResource(id = R.string.no_account), fontSize = 15.sp, color = Azul)
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}
