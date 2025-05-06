package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watchlist.ui.theme.WatchListTheme

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize ViewModel
        viewModel = ViewModelProvider(this, MyViewModelFactory(application))[MyViewModel::class.java]

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

    // Factory for creating the ViewModel.  This is the key part.
    class MyViewModelFactory(private val application: android.app.Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MyViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}