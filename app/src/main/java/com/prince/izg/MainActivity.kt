package com.prince.izg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModelFactory
import com.prince.izg.data.local.datastore.DataStoreManager
import com.prince.izg.data.remote.api.AuthApi
import com.prince.izg.data.repository.AuthRepository
import com.prince.izg.navigation.AuthNavGraph
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    companion object {
        private const val BASE_URL = "https://izg-backend.onrender.com/api/" // Replace with actual base URL
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MANUAL Dependency Setup
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authApi = retrofit.create(AuthApi::class.java)
        val authRepository = AuthRepository(authApi)
        val dataStoreManager = DataStoreManager(applicationContext)

        val authViewModelFactory = AuthViewModelFactory(authRepository, dataStoreManager)
        val authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]

        setContent {
            IZGApp(authViewModel)
        }
    }
}

@Composable
fun IZGApp(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    AuthNavGraph(
        navController = navController,
        authViewModel = authViewModel
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IZGAppPreview() {
    // Do not preview manually injected ViewModel in this state
}