package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SecondScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            //AppTopBar(title = "Second Screen") {
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
            Text("This is the second screen")
        }
    }
}

