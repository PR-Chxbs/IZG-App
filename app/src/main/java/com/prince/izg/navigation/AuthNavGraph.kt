package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.prince.izg.auth.ui.LoginScreen
import com.prince.izg.auth.ui.RegisterScreen
import com.prince.izg.auth.viewmodel.AuthViewModel

@Composable
fun AuthNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel  // <-- Now passed as a parameter
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onLoginSuccess = {
                    if (authViewModel.isAdmin.value) {
                        navController.navigate(Graph.ADMIN) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    } else {
                        navController.navigate(Graph.USER) {
                            popUpTo(Graph.AUTH) { inclusive = true }
                        }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
    }
}
