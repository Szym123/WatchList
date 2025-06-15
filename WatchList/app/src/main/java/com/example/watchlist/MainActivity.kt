package com.example.watchlist

import DetailsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.watchlist.ui.theme.WatchListTheme
import androidx.compose.runtime.LaunchedEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        var keepSplashScreenOn = true
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }

        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "user_database"
        ).build()

        val database2 = Room.databaseBuilder(
            applicationContext,
            AppDatabase2::class.java,
            "user_credentials"
        ).build()

        val userViewModelFactory = UserViewModelFactory(database)
        val authViewModelFactory = AuthViewModelFactory(database2)

        setContent {
            val navController = rememberNavController()
            val userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)
            val authViewModel: AuthViewModel = viewModel(factory = authViewModelFactory)
            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(systemDarkTheme) }

            WatchListTheme(darkTheme = isDarkTheme) {
                NavigationGraph(
                    navController = navController,
                    userViewModel = userViewModel,
                    authViewModel = authViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = { isDarkTheme = it },
                    onDestinationDetermined = {
                        keepSplashScreenOn = false
                    }
                )
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onDestinationDetermined: () -> Unit
) {
    var startDestination by remember { mutableStateOf<String?>(null) }

    // password part
    LaunchedEffect(Unit) {
        val credentials = authViewModel.getCredentials()
        startDestination = if (credentials != null) {
            if (credentials.enabled && credentials.passwordHash != null) {
                "login"
            }  else {
                "main"
            }
        } else {
            "main"
        }
        onDestinationDetermined()
    }

    if (startDestination != null) {
        NavHost(navController = navController, startDestination = startDestination!!) {
            composable("settings") {
                SettingsScreen(
                    navController = navController,
                    userViewModel = userViewModel,
                    authViewModel = authViewModel,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange
                )
            }
            composable("main") { MainScreen(navController, userViewModel) }
            composable("input") { InputScreen(navController, userViewModel) }
            composable("password") {
                PasswordScreen(
                    navController = navController,
                    authViewModel = authViewModel,
                )
            }
            composable("login") {
                LoginScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }
            composable("details") { DetailsScreen(navController, userViewModel) }
        }
    }
}