package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watchlist.ui.theme.WatchListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchListTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("input") { InputScreen(navController) }
                    composable("settings") { SettingsScreen(navController) }
                    composable("password") { PasswordScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("details") { DetailsScreen(navController) }
                }
            }
        }
    }
}
