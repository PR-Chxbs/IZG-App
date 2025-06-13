package com.prince.izg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.prince.izg.navigation.AuthNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IZGApp()
        }
    }
}

@Composable
fun IZGApp() {
    val navController = rememberNavController()
    AuthNavGraph(navController = navController)
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IZGAppPreview() {
    IZGApp()
}