package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

import com.prince.izg.ui.endpoints.admin.ui.DashboardScreen
import com.prince.izg.ui.endpoints.admin.ui.EventsScreen
import com.prince.izg.ui.endpoints.admin.ui.PostScreen
import com.prince.izg.ui.endpoints.admin.ui.StockScreen
import com.prince.izg.ui.endpoints.admin.ui.UsersScreen

@Composable
fun NavGraphBuilder.AdminNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.AdminDashboard.route,
        route = Graph.ADMIN
    ) {
        composable(Screen.AdminDashboard.route) { DashboardScreen() }
        composable(Screen.ManageUsers.route) { UsersScreen() }
        composable(Screen.ManageEvents.route) { EventsScreen() }
        composable(Screen.ManagePosts.route) { PostScreen() }
        composable(Screen.ManageStock.route) { StockScreen() }
    }
}
