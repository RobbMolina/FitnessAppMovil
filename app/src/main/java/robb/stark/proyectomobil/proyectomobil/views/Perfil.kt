package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import robb.stark.proyectomobil.proyectomobil.models.Preferencias
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.launch
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.Preferencias.Companion.dataStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Perfil(navController: NavController) {

    val Morado = Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val GrisClaro = Color(0xF2807F7F)
    val Grisfondo = Color(0x9CE7E9EC)

    val coroutine = rememberCoroutineScope()
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

        Column(Modifier.fillMaxWidth().align(Alignment.Center), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "$savedUsername", fontSize = 30.sp, textAlign = TextAlign.Center)
            Text(text = stringResource(id = R.string.member), fontSize = 20.sp, color = Color(0xFF969696), textAlign = TextAlign.Center)

            Image(
                painter = painterResource(id = R.drawable.usercaract),
                contentDescription = stringResource(id = R.string.profile_background),
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
                Text(text = stringResource(id = R.string.motivation), fontSize = 20.sp, color = GrisClaro, textAlign = TextAlign.Justify)
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(10.dp))
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
                        Text(text = "${stringResource(id = R.string.age)}: ", fontSize = 20.sp)
                        Text(text = "${stringResource(id = R.string.height)}: ", fontSize = 20.sp)
                        Text(text = "${stringResource(id = R.string.weight)}: ", fontSize = 20.sp)
                        Text(text = "${stringResource(id = R.string.activity_level)}: ", fontSize = 20.sp)
                        Text(text = "${stringResource(id = R.string.goal)}: ", fontSize = 20.sp)
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
                    onClick = {
                        coroutine.launch {
                            context.dataStore.edit { settings ->
                                settings.clear()
                            }
                        }
                    }
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = stringResource(id = R.string.go_to_workout),
                            fontSize = 20.sp,
                            color = Violeta,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                FloatingActionButton(
                    onClick = { navController.navigate("plan") },
                    modifier = Modifier.weight(1f)
                ) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Text(
                            text = stringResource(id = R.string.view_diet_plan),
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
            IconButton(onClick = { System.exit(0)}) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = stringResource(id = R.string.exit), tint = Violeta,
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
            IconButton(onClick = { navController.navigate("plan_alimenticio")}) {
                Icon(imageVector = Icons.Default.Fastfood, contentDescription = stringResource(id = R.string.diet), tint = Violeta,
                    modifier = Modifier
                        .size(35.dp)
                        .scale(-1f, 1f))
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
