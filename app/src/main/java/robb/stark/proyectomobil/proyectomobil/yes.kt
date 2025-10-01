package robb.stark.proyectomobil.proyectomobil

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.Preferencias

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun yes() {

    val Morado = Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val GrisClaro = Color(0xF2807F7F)
    val Grisfondo = Color(0x9CE7E9EC)

    val context = LocalContext.current
    val preferencias = Preferencias(context)

    val savedUsername by preferencias.username.collectAsState(initial = "")
    val savedPassword by preferencias.password.collectAsState(initial = "")


    val savedAge by preferencias.age.collectAsState(initial = 0)
    val savedHeight by preferencias.height.collectAsState(initial = 0f)
    val savedWeight by preferencias.weight.collectAsState(initial = 0f)
    val savedActivity by preferencias.activity.collectAsState(initial = "---")
    val savedGoal by preferencias.goal.collectAsState(initial = "---")

    Box(Modifier.fillMaxSize().padding(15.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "icono",
                    tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f)
                )
            }
            Text( text = "Perfil", fontSize = 20.sp)
            Icon(imageVector = Icons.Default.Notifications, contentDescription = "icono",
                tint = Violeta,
                modifier = Modifier
                    .size(35.dp)
                    .scale(-1f, 1f))

        }

        Column(Modifier.fillMaxWidth().align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Roberto$savedUsername", fontSize = 30.sp, textAlign = TextAlign.Center)
            Text(text = "Miembro", fontSize = 20.sp, color = Color(0xFF969696), textAlign = TextAlign.Center)

            Image(
                painter = painterResource(id = R.drawable.usercaract),
                contentDescription = "fondo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.width(250.dp)
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.Gray, spotColor = Color.Gray.copy(alpha = 0.2f))
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Recuerda que no tiene nada de malo fallar un dia! Lo importante es mantenerse constante ", fontSize = 20.sp, color = GrisClaro, textAlign = TextAlign.Justify)
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(10.dp))
//                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
//                    Text(text = "Lvl 2", fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Violeta)
//                    Box(modifier = Modifier
//                        .width(200.dp)
//                        .height(10.dp)
//                        .clip(RoundedCornerShape(50))
//                        .background(Color.LightGray)
//                    ) {LinearProgressIndicator(
//                        progress = .5f,
//                        color = Violeta,
//                        trackColor = Color.Transparent,
//                        modifier = Modifier.fillMaxSize()
//                    )
//                    }
//                    Text(text = "50%", fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Violeta)
//                }

            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .shadow(16.dp, shape = RoundedCornerShape(16.dp), ambientColor = Color.Gray, spotColor = Color.Gray.copy(alpha = 0.2f))
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = Color.White)
                    .padding(10.dp),
            ) {
                Row {
                    Column(Modifier.fillMaxWidth().weight(1f)){
                        Text(text = "Edad: ", fontSize = 20.sp)
                        Text(text = "Altura: ", fontSize = 20.sp)
                        Text(text = "Peso: ", fontSize = 20.sp)
                        Text(text = "Nivel de actividad: ", fontSize = 20.sp)
                        Text(text = "Objetivo: ", fontSize = 20.sp)
                    }
                    Column(Modifier.fillMaxWidth().weight(1f)){
                        Text(text = "$savedAge", fontSize = 20.sp)
                        Text(text = "${savedHeight} cm", fontSize = 20.sp)
                        Text(text = "${savedWeight} kg", fontSize = 20.sp)
                        Text(text = "$savedActivity", fontSize = 20.sp)
                        Text(text = "$savedGoal", fontSize = 20.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row {
                FloatingActionButton(
                    onClick = { /* acción */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Ir al entrenamiento",
                            fontSize = 20.sp,
                            color = Violeta,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                FloatingActionButton(
                    onClick = { /* acción */ },
                    modifier = Modifier.weight(1f)
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = "Ver plan alimenticio",
                            fontSize = 20.sp,
                            color = Violeta,
                            textAlign = TextAlign.Center
                        )
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
                .padding(15.dp), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "salir", tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Calculate, contentDescription = "calculadora de macros", tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "home", tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Fastfood, contentDescription = "dieta", tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
            IconButton(onClick = {}) {
                Icon(imageVector = Icons.Default.DirectionsRun, contentDescription = "dieta", tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
            }
        }
    }
}
