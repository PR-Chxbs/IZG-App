package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.izg.ui.endpoints.auth.ui.LoginScreen
import com.prince.izg.ui.endpoints.auth.ui.RegisterScreen
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    navigation(
        startDestination = Screen.Login.route,
        route = Graph.AUTH
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    val target = if (authViewModel.isAdmin.value) Graph.ADMIN else Graph.USER
                    navController.navigate(target) {
                        popUpTo(Graph.AUTH) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    authViewModel.clearError()
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    authViewModel.clearError()
                    navController.popBackStack()
                }
            )
        }
    }
}
