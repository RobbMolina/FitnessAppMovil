package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.food
import robb.stark.proyectomobil.proyectomobil.viewmodels.FoodCard


@ExperimentalMaterial3Api
@Composable

fun Plan(navController: NavController){

    val Morado= Color(0xFF331E36)
    val Violeta = Color(0xFF41337A)
    val Azul = Color(0xFF6EA4BF)
    val GrisClaro = Color(0xF2807F7F)
    val Grisfondo = Color(0x9CE7E9EC)

Box(Modifier.fillMaxSize().padding(15.dp)) {
    Row(
        Modifier.fillMaxWidth().align(Alignment.TopCenter),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { navController.navigate("perfil") }) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "icono",
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f)
            )
        }
        Text(text = "Perfil", fontSize = 20.sp)
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "icono",
            tint = Violeta,
            modifier = Modifier.size(35.dp).scale(-1f, 1f)
        )
    }

    Column(Modifier.align(Alignment.Center).height(700.dp)) {

            val foods = listOf(
                food(1, "Ensalada César", "Ensalada con pollo, lechuga, aderezo César", 150f, 15f, 8f, 7f,img = R.drawable.salad),
                food(2, "Tacos de Pollo", "Tacos con pollo, salsa y vegetales", 250f, 20f, 20f, 10f, img =  R.drawable.taco),
                food(3, "Pizza Margherita", "Pizza con salsa de tomate, mozzarella y albahaca", 300f, 12f, 30f, 15f, R.drawable.pizza),
                food(4, "Hamburguesa Clásica", "Hamburguesa de carne con queso, pan y vegetales", 400f, 25f, 30f, 20f, R.drawable.burger),
                food(5, "Sushi", "Sushi con atún, arroz y algas", 200f, 15f, 25f, 8f, R.drawable.sushi),
                food(6, "Spaghetti Bolognese", "Espaguetis con salsa boloñesa", 350f, 20f, 45f, 15f, R.drawable.spaguetti),
                food(7, "Pollo al Horno", "Pollo asado con hierbas", 280f, 30f, 10f, 12f, R.drawable.chicken),
                food(8, "Ensalada de Frutas", "Frutas frescas variadas", 120f, 2f, 30f, 1f, R.drawable.fruit_salad),
                food(9, "Pasta Carbonara", "Pasta con salsa carbonara de huevo y queso", 400f, 15f, 45f, 18f, R.drawable.carbonara),
                food(10, "Tacos de Carne Asada", "Tacos con carne asada, cebolla y cilantro", 350f, 25f, 30f, 18f, R.drawable.taco_carne),
                food(11, "Falafel", "Bolas de garbanzo fritas servidas con ensalada", 250f, 12f, 35f, 10f, R.drawable.falafel),
                food(12, "Hummus con Pan de Pita", "Hummus con pan de pita", 200f, 8f, 25f, 10f, R.drawable.hummus),
                food(13, "Paella", "Arroz con mariscos, pollo y verduras", 400f, 20f, 45f, 18f, R.drawable.paella),
                food(14, "Ensalada Griega", "Ensalada con pepino, tomate, queso feta y aceitunas", 180f, 5f, 12f, 10f, R.drawable.greek_salad),
                food(15, "Wrap de Pollo", "Wrap con pollo, lechuga, tomate y aderezo", 280f, 25f, 25f, 12f, R.drawable.wrap),
                food(16, "Croissants", "Croissants rellenos de mantequilla", 320f, 6f, 30f, 20f, R.drawable.croissant),
                food(17, "Batido de Proteína", "Batido de proteína con leche", 150f, 25f, 10f, 3f, R.drawable.protein_shake),
                food(18, "Panqueques", "Panqueques con miel y frutas", 350f, 8f, 50f, 15f, R.drawable.pancakes),
                food(19, "Chili con Carne", "Chili con carne, frijoles y especias", 400f, 25f, 40f, 18f, R.drawable.chili),
                food(20, "Tarta de Manzana", "Tarta con manzana y masa crujiente", 280f, 2f, 40f, 12f, R.drawable.apple_pie)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(foods) { foodItem ->
                    FoodCard(foodItem, navController)
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
                contentDescription = "salir",
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f)
            )
        }
        IconButton(onClick = { navController.navigate("calculadora") }) {
            Icon(
                imageVector = Icons.Default.Calculate,
                contentDescription = "calculadora de macros",
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f)
            )
        }
        IconButton(onClick = { navController.navigate("home") }) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "home",
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f)
            )
        }
        IconButton(onClick = { navController.navigate("plan") }) {
            Icon(
                imageVector = Icons.Default.Fastfood,
                contentDescription = "dieta",
                tint = Violeta,
                modifier = Modifier.size(35.dp).scale(-1f, 1f)
            )
        }
        IconButton(onClick = { navController.navigate("Rutina") }) {
            Icon(
                imageVector = Icons.Default.DirectionsRun,
                contentDescription = "rutina",
                tint = Violeta,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
}
