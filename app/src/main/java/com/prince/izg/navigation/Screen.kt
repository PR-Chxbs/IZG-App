package com.prince.izg.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
}