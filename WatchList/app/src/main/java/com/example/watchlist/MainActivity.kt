package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.watchlist.ui.theme.WatchListTheme
import java.io.File

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        val userViewModelFactory = UserViewModelFactory(database)

        setContent {
            val userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)

            WatchListTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController, userViewModel) }
                    composable("input") { InputScreen(navController, userViewModel) }
                    composable("settings") { SettingsScreen(navController, userViewModel) }
                    composable("password") { PasswordScreen(navController) }
                    composable("login") { LoginScreen(navController) }
                    composable("details") { DetailsScreen(navController) }
                }
            }
        }
    }
}