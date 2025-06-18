package com.prince.izg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.izg.ui.endpoints.user.ui.*

fun NavGraphBuilder.userNavGraph(navController: NavController) {
    navigation(
        startDestination = Screen.UserHome.route,
        route = Graph.USER
    ) {
        composable(Screen.UserHome.route) { HomeScreen() }
        composable(Screen.UserEvents.route) { EventsScreen() }
        composable(Screen.UserPosts.route) { PostsScreen() }
    }
}
