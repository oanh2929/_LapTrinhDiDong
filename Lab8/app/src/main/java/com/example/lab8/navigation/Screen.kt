package com.example.lab8.navigation

sealed class Screen(val route: String) {

    object SignIn : Screen("signin")
    object SignUp : Screen("signup")
    object Order : Screen("order")

    // nếu chị dùng Home thì mở dòng này
    object Home : Screen("home")
}