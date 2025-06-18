package com.prince.izg.navigation

sealed class Screen(val route: String) {
    // Auth screens
    object Login : Screen("login")
    object Register : Screen("register")

    // Admin screens
    object AdminDashboard : Screen("admin_dashboard")
    object ManageUsers : Screen("manage_users")
    object ManageEvents : Screen("manage_events")
    object ManagePosts : Screen("manage_posts")
    object ManageStock : Screen("manage_stock")

    // User screens
    object UserHome : Screen("user_home")
    object UserEvents : Screen("user_events")
    object UserPosts : Screen("user_posts")

    // Navs
    object Auth : Screen("auth")   // For NavHost startDestination
    object User : Screen("user")   // Root of user graph
    object Admin : Screen("admin") // Root of admin graph
}