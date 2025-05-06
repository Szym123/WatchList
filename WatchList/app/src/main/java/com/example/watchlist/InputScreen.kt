package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun InputScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = true,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            BottomAppBarExample()
        },
        /*floatingActionButton = {
             FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
                )
             }
        }*/
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text("This is the input screen")
        }
    }
}

