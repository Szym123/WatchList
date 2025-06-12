package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.watchlist.ui.theme.WatchListTheme

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
            val navController = rememberNavController()
            val userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemDarkTheme) }

            WatchListTheme(darkTheme = isDarkTheme) {
                NavigationGraph(
                    navController = navController,
                    userViewModel = userViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it }
                )
            }
        }
    }
}

// Ta definicja NavigationGraph jest poprawna i powinna zostać
@Composable
fun NavigationGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    NavHost(navController = navController, startDestination = "settings") {
        composable("settings") {
            SettingsScreen(
                navController = navController,
                userViewModel = userViewModel,
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
        composable("main") { MainScreen(navController, userViewModel) }
        composable("input") { InputScreen(navController, userViewModel) }
        composable("password") { PasswordScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("details") { DetailsScreen(navController) }
    }
}