package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            //AppTopBar(title = "Main Screen") {
                // Handle button click
            //}
            CenterAlignedTopAppBarExample()
        },
        bottomBar = {
            AppNavigationBar(navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { navController.navigate("second") }) {
                Text("Go to Second Screen")
            }
        }
    }
}

