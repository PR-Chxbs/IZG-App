package com.prince.izg.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.prince.izg.R
import com.prince.izg.ui.components.post.ReadArticleScreen
import com.prince.izg.ui.components.shared.BottomNavBar
import com.prince.izg.ui.components.shared.BottomNavItem
import com.prince.izg.ui.endpoints.user.ui.PostsScreen
import com.prince.izg.ui.endpoints.admin.viewmodel.event.EventViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModel
import com.prince.izg.ui.endpoints.user.ui.*

fun NavGraphBuilder.userNavGraph(
    navController: NavController,
    eventViewModel: EventViewModel,
    postViewModel: PostViewModel,
    token: String
) {
    navigation(
        startDestination = Screen.UserHome.route,
        route = Graph.USER
    ) {

        val bottomNavItems = listOf(
            BottomNavItem(
                route = Screen.UserHome.route,
                label = "Home",
                // iconRes = R.drawable.ic_dashboard
                iconRes = R.drawable.ic_home_icon
            ),
            BottomNavItem(
                route = Screen.UserPosts.route,
                label = "Articles",
                iconRes = R.drawable.ic_article_icon
            ),
            BottomNavItem(
                route = Screen.UserEvents.route,
                label = "Events",
                iconRes = R.drawable.ic_event_icon
            )

        )

        composable(Screen.UserHome.route) {
            HomeScreen(
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.UserHome.route
                    )
                }
            )
        }

        composable(Screen.UserPosts.route) {
            PostsScreen(
                viewModel = postViewModel,
                token = token,
                navController = navController,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.UserPosts.route
                    )
                }
            )
        }

        composable(Screen.UserEvents.route) {
            EventsScreen(
                viewModel = eventViewModel,
                token = token,
                bottomBar = {
                    BottomNavBar(
                        items = bottomNavItems,
                        navController = navController,
                        currentRoute = Screen.UserEvents.route
                    )
                }
            )
        }

        composable(Screen.UserReadPost.route) { backStackEntry ->
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
                        currentRoute = Screen.UserPosts.route
                    )
                }
            )
        }
    }
}
