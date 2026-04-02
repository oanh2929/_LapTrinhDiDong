package com.example.lab8.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.lab8.*

@Composable
fun MyNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.SignIn.route
    ) {

        composable(Screen.SignIn.route) {
            SignIn(navController)
        }

        composable(Screen.SignUp.route) {
            SignUp(navController)
        }


        composable("home") {
            HomeScreen(navController)
        }

        composable(Screen.Order.route) {
            OrderScreen(navController)
        }
    }
}