package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prince.izg.auth.ui.LoginScreen
import com.prince.izg.auth.ui.RegisterScreen
import com.prince.izg.auth.viewmodel.AuthViewModel

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (authViewModel.isLoggedIn.value) {
            if (authViewModel.isAdmin.value) Screen.Admin.route else Screen.User.route
        } else {
            Screen.Auth.route
        }
    ) {
        authNavGraph(navController, authViewModel)
        userNavGraph(navController)
        adminNavGraph(navController)
    }
}