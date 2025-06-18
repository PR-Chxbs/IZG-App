package com.prince.izg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.izg.R
import com.prince.izg.ui.components.shared.BottomNavBar
import com.prince.izg.ui.components.shared.BottomNavItem
import com.prince.izg.ui.endpoints.admin.ui.*
import com.prince.izg.ui.endpoints.admin.ui.dashboard.DashboardScreen
import com.prince.izg.ui.endpoints.admin.viewmodel.category.CategoryViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.event.EventViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.stock.StockViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel

fun NavGraphBuilder.adminNavGraph(
    navController: NavController,
    userViewModel: UserViewModel,
    categoryViewModel: CategoryViewModel,
    eventViewModel: EventViewModel,
    postViewModel: PostViewModel,
    stockViewModel: StockViewModel,
    token: String
) {
    navigation(
        startDestination = Screen.AdminDashboard.route,
        route = Graph.ADMIN
    ) {
        val bottomNavItems = listOf(
            BottomNavItem(
                route = Screen.AdminDashboard.route,
                label = "Home",
                iconRes = R.drawable.ic_dashboard
            ),
            BottomNavItem(
                route = Screen.ManageUsers.route,
                label = "Team",
                iconRes = R.drawable.ic_users
            ),
            BottomNavItem(
                route = Screen.ManageStock.route,
                label = "Stock",
                iconRes = R.drawable.ic_post
            ),
            BottomNavItem(
                route = Screen.ManagePosts.route,
                label = "Articles",
                iconRes = R.drawable.ic_post
            ),
            BottomNavItem(
                route = Screen.ManageEvents.route,
                label = "Events",
                iconRes = R.drawable.ic_post
            )

        )
        composable(Screen.AdminDashboard.route) {
            DashboardScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.AdminDashboard.route
                    )
                }
            )
        }
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
