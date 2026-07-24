package robb.stark.proyectomobil.proyectomobil.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import robb.stark.proyectomobil.R
import robb.stark.proyectomobil.proyectomobil.models.food
import robb.stark.proyectomobil.proyectomobil.viewmodels.FoodCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Plan(navController: NavController) {

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = { AppBottomBar(navController, currentRoute = "plan") }
    ) { innerPadding ->
        AppBackground {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(12.dp))
                Spacer(Modifier.height(16.dp))
                Text(
                    text = stringResourceOrDefault(),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.Morado,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(12.dp))

                val foods = listOf(
                    food(1, "Caesar Salad", "Salad with chicken, lettuce, and Caesar dressing", 150f, 15f, 8f, 7f, img = R.drawable.salad),
                    food(2, "Chicken Tacos", "Tacos with chicken, salsa, and vegetables", 250f, 20f, 20f, 10f, img = R.drawable.taco),
                    food(3, "Margherita Pizza", "Pizza with tomato sauce, mozzarella, and basil", 300f, 12f, 30f, 15f, R.drawable.pizza),
                    food(4, "Classic Burger", "Beef burger with cheese, bun, and vegetables", 400f, 25f, 30f, 20f, R.drawable.burger),
                    food(5, "Sushi", "Sushi with tuna, rice, and seaweed", 200f, 15f, 25f, 8f, R.drawable.sushi),
                    food(6, "Spaghetti Bolognese", "Spaghetti with Bolognese sauce", 350f, 20f, 45f, 15f, R.drawable.spaguetti),
                    food(7, "Roasted Chicken", "Roasted chicken with herbs", 280f, 30f, 10f, 12f, R.drawable.chicken),
                    food(8, "Fruit Salad", "Fresh mixed fruits", 120f, 2f, 30f, 1f, R.drawable.fruit_salad),
                    food(9, "Pasta Carbonara", "Pasta with egg and cheese carbonara sauce", 400f, 15f, 45f, 18f, R.drawable.carbonara),
                    food(10, "Grilled Beef Tacos", "Tacos with grilled beef, onion, and cilantro", 350f, 25f, 30f, 18f, R.drawable.taco_carne),
                    food(11, "Falafel", "Fried chickpea balls served with salad", 250f, 12f, 35f, 10f, R.drawable.falafel),
                    food(12, "Hummus with Pita Bread", "Hummus served with pita bread", 200f, 8f, 25f, 10f, R.drawable.hummus),
                    food(13, "Paella", "Rice with seafood, chicken, and vegetables", 400f, 20f, 45f, 18f, R.drawable.paella),
                    food(14, "Greek Salad", "Salad with cucumber, tomato, feta cheese, and olives", 180f, 5f, 12f, 10f, R.drawable.greek_salad),
                    food(15, "Chicken Wrap", "Wrap with chicken, lettuce, tomato, and dressing", 280f, 25f, 25f, 12f, R.drawable.wrap),
                    food(16, "Croissants", "Butter-filled croissants", 320f, 6f, 30f, 20f, R.drawable.croissant),
                    food(17, "Protein Shake", "Protein shake with milk", 150f, 25f, 10f, 3f, R.drawable.protein_shake),
                    food(18, "Pancakes", "Pancakes with honey and fruit", 350f, 8f, 50f, 15f, R.drawable.pancakes),
                    food(19, "Chili con Carne", "Chili with beef, beans, and spices", 400f, 25f, 40f, 18f, R.drawable.chili),
                    food(20, "Apple Pie", "Apple pie with a crispy crust", 280f, 2f, 40f, 12f, R.drawable.apple_pie)
                )

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 20.dp)
                ) {
                    items(foods) { foodItem ->
                        FoodCard(foodItem, navController)
                    }
                }
            }
        }
    }
}

/**
 * Título de la pantalla. Se deja como función auxiliar por si se agrega
 * un recurso de string dedicado (p. ej. R.string.diet_plan_title) más adelante.
 */
@Composable
private fun stringResourceOrDefault(): String = "Meal Collection"