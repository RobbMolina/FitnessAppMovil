package robb.stark.proyectomobil.proyectomobil

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import robb.stark.proyectomobil.proyectomobil.viewmodels.EjercicioViewModel
import robb.stark.proyectomobil.proyectomobil.views.Calculadora
import robb.stark.proyectomobil.proyectomobil.views.Home
import robb.stark.proyectomobil.proyectomobil.views.LoginView
import robb.stark.proyectomobil.proyectomobil.views.Perfil
import robb.stark.proyectomobil.proyectomobil.views.Plan
import robb.stark.proyectomobil.proyectomobil.views.Rutina

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun NavigationManager(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Inicio") {
        composable(route= "Inicio"){
            LoginView(navController)
        }


        composable(route = "Home", arguments = listOf()) {
            Home(navController)
        }

        composable(route = "Perfil", arguments = listOf()) {
            Perfil(navController)
        }

        composable(route= "calculadora"){
            Calculadora(navController)
        }

        composable(route= "plan"){
            Plan(navController)
        }


        composable(route= "Rutina"){
            Rutina(navController, EjercicioViewModel())
        }
    }
}