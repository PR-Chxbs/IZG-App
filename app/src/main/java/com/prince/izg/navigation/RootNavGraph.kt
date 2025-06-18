package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.collectAsState
import com.prince.izg.data.remote.api.UserApi
import com.prince.izg.data.repository.UserRepository
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    retrofit: Retrofit
) {
    val token by authViewModel.authToken.collectAsState()
    val isAdmin by authViewModel.isAdmin.collectAsState()
    val isLoggedIn = !token.isNullOrEmpty()

    val userApi = remember(retrofit) { retrofit.create(UserApi::class.java) }
    val userRepository = remember(userApi, token) { UserRepository(userApi) }
    val userViewModel = remember(userRepository) { UserViewModel(userRepository) }

    NavHost(
        navController = navController,
        startDestination = when {
            !isLoggedIn -> Graph.AUTH
            isAdmin -> Graph.ADMIN
            else -> Graph.USER
        }
    ) {
        authNavGraph(navController, authViewModel)
        userNavGraph(navController)
        adminNavGraph(navController, userViewModel, token.orEmpty())
    }
}