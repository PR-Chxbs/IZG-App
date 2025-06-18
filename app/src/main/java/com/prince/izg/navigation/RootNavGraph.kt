package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModel

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
        AuthNavGraph(navController, authViewModel)
        userNavGraph(navController)
        AdminNavGraph(navController)
    }
}