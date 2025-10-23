package com.prince.izg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.izg.R
import com.prince.izg.ui.components.post.CreateArticleScreen
import com.prince.izg.ui.components.post.ReadArticleScreen
import com.prince.izg.ui.components.shared.BottomNavBar
import com.prince.izg.ui.components.shared.BottomNavItem
import com.prince.izg.ui.components.stock.AddOrEditStockScreen
import com.prince.izg.ui.components.users.AddOrEditUserScreen
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
                // iconRes = R.drawable.ic_dashboard
                iconRes = R.drawable.ic_home_icon
            ),
            BottomNavItem(
                route = Screen.ManageUsers.route,
                label = "Team",
                iconRes = R.drawable.ic_user_icon
            ),
            BottomNavItem(
                route = Screen.ManageStock.route,
                label = "Stock",
                iconRes = R.drawable.ic_stock_icon
            ),
            BottomNavItem(
                route = Screen.ManagePosts.route,
                label = "Articles",
                iconRes = R.drawable.ic_article_icon
            ),
            BottomNavItem(
                route = Screen.ManageEvents.route,
                label = "Events",
                iconRes = R.drawable.ic_event_icon
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
                    navController.navigate("editUser/${userId}")
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManageUsers.route
                    )
                }
            )
        }
        composable(Screen.ManageEvents.route) { EventsScreen() }

        composable(Screen.ManagePosts.route) {
            PostScreen(
                viewModel = postViewModel,
                token = token,
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManagePosts.route
                    )
                }
            )
        }

        composable(Screen.ManageStock.route) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: -1
            StockScreen(
                viewModel = stockViewModel,
                token = token,
                navToAddOrEditStock = { stockId ->
                    val route = stockId?.let { "addOrEditStock/$it" } ?: "addOrEditStock"
                    navController.navigate(route)
                },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManageStock.route
                    )
                }
            )
        }

        composable(Screen.AddOrEditUser.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")?.toIntOrNull() ?: -1
            AddOrEditUserScreen(
                userId = userId,
                token = token,
                userViewModel = userViewModel,
                onBack = { navController.popBackStack() },
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManageUsers.route
                    )
                }
            )
        }

        composable(Screen.CreatePost.route) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: -1
            CreateArticleScreen(
                navController = navController,
                token = token,
                viewModel = postViewModel,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManagePosts.route
                    )
                },
                postId = postId
            )
        }

        composable(Screen.ReadPost.route) { backStackEntry ->
            val postId = backStackEntry.arguments?.getString("postId")?.toIntOrNull() ?: -1
            ReadArticleScreen(
                navController = navController,
                postId = postId,
                viewModel = postViewModel,
                token = token,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManagePosts.route
                    )
                }
            )
        }

        composable(Screen.AddOrEditStock.route) { backStackEntry ->
            val stockId = backStackEntry.arguments?.getString("stockId")?.toIntOrNull() ?: -1
            AddOrEditStockScreen(
                stockId = stockId,
                token = token,
                stockViewModel = stockViewModel,
                onBack = { navController.popBackStack() },
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.ManageStock.route
                    )
                }
            )
        }

    }
}
