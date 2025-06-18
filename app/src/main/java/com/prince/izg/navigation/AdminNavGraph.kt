package com.prince.izg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.izg.ui.endpoints.admin.ui.*
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel

fun NavGraphBuilder.adminNavGraph(
    navController: NavController,
    userViewModel: UserViewModel,
    token: String
) {
    navigation(
        startDestination = Screen.AdminDashboard.route,
        route = Graph.ADMIN
    ) {
        composable(Screen.AdminDashboard.route) { DashboardScreen() }
        composable(Screen.ManageUsers.route) {
            UsersScreen(
                viewModel = userViewModel,
                token = token,
                onEditUser = { userId ->
                    // Navigate to EditUser screen (if you implement one later)
                    // Example: navController.navigate("editUser/$userId")
                }
            )
        }
        composable(Screen.ManageEvents.route) { EventsScreen() }
        composable(Screen.ManagePosts.route) { PostScreen() }
        composable(Screen.ManageStock.route) { StockScreen() }
    }
}
