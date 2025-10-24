package com.prince.izg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.prince.izg.data.local.datastore.DataStoreManager
import com.prince.izg.data.remote.api.AuthApi
import com.prince.izg.data.repository.AuthRepository
import com.prince.izg.navigation.RootNavGraph
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModel
import com.prince.izg.ui.endpoints.auth.viewmodel.AuthViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    companion object {
        private const val BASE_URL = "https://izg-backend.onrender.com/api/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)  // for Render cold starts
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()

        // Retrofit setup
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Auth-related dependencies
        val authApi = retrofit.create(AuthApi::class.java)
        val authRepository = AuthRepository(authApi)
        val dataStoreManager = DataStoreManager(applicationContext)
        val authViewModelFactory = AuthViewModelFactory(authRepository, dataStoreManager)
        val authViewModel = ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]

        setContent {
            IZGApp(authViewModel = authViewModel, retrofit = retrofit)
        }
    }
}

@Composable
fun IZGApp(authViewModel: AuthViewModel, retrofit: Retrofit) {
    val navController = rememberNavController()
    RootNavGraph(
        navController = navController,
        authViewModel = authViewModel,
        retrofit = retrofit // pass it down
    )
}

@Preview(showBackground = true)
@Composable
fun IZGAppPreview() {
    // You can't preview ViewModels or Retrofit without mocking
}
