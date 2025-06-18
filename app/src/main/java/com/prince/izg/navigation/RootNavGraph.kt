package com.prince.izg.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prince.izg.data.remote.api.*
import com.prince.izg.data.repository.*
import com.prince.izg.ui.endpoints.admin.viewmodel.category.CategoryViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.category.CategoryViewModelFactory
import com.prince.izg.ui.endpoints.admin.viewmodel.event.EventViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.event.EventViewModelFactory
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModelFactory
import com.prince.izg.ui.endpoints.admin.viewmodel.stock.StockViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.stock.StockViewModelFactory
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModelFactory
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RootNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    retrofit: Retrofit
) {
    val context = LocalContext.current
    val token by authViewModel.authToken.collectAsState()
    val isAdmin by authViewModel.isAdmin.collectAsState()
    val isLoggedIn = !token.isNullOrEmpty()

    // --- API Instances ---
    val userApi = retrofit.create(UserApi::class.java)
    val categoryApi = retrofit.create(CategoryApi::class.java)
    val eventApi = retrofit.create(EventApi::class.java)
    val postApi = retrofit.create(PostApi::class.java)
    val stockApi = retrofit.create(StockApi::class.java)

    // --- Repository Instances ---
    val userRepo = remember { UserRepository(userApi) }
    val categoryRepo = remember { CategoryRepository(categoryApi) }
    val eventRepo = remember { EventRepository(eventApi) }
    val postRepo = remember { PostRepository(postApi) }
    val stockRepo = remember { StockRepository(stockApi) }

    // --- ViewModel Factories ---
    val userViewModelFactory = remember { UserViewModelFactory(userRepo) }
    val categoryViewModelFactory = remember { CategoryViewModelFactory(categoryRepo) }
    val eventViewModelFactory = remember { EventViewModelFactory(eventRepo) }
    val postViewModelFactory = remember { PostViewModelFactory(postRepo) }
    val stockViewModelFactory = remember { StockViewModelFactory(stockRepo) }

    // --- ViewModels via ViewModelProvider ---
    val userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
    val categoryViewModel: CategoryViewModel = viewModel(factory = categoryViewModelFactory)
    val eventViewModel: EventViewModel = viewModel(factory = eventViewModelFactory)
    val postViewModel: PostViewModel = viewModel(factory = postViewModelFactory)
    val stockViewModel: StockViewModel = viewModel(factory = stockViewModelFactory)

    // --- Navigation ---
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
        adminNavGraph(
            navController = navController,
            userViewModel = userViewModel,
            categoryViewModel = categoryViewModel,
            eventViewModel = eventViewModel,
            postViewModel = postViewModel,
            stockViewModel = stockViewModel,
            token = token.orEmpty()
        )
    }
}
