package com.example.watchlist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable


@Composable
fun BottomAppBarExample() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Localized description",
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = "Localized description",
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = false,
            onClick = { navController.navigate("main") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Second") },
            label = { Text("Input") },
            selected = false,
            onClick = { navController.navigate("input") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Second") },
            label = { Text("Settings") },
            selected = false,
            onClick = { navController.navigate("settings") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Second") },
            label = { Text("Password") },
            selected = false,
            onClick = { navController.navigate("password") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Second") },
            label = { Text("login") },
            selected = false,
            onClick = { navController.navigate("login") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Second") },
            label = { Text("details") },
            selected = false,
            onClick = { navController.navigate("details") }
        )
    }
}