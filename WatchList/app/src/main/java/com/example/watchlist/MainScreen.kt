package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = false,
                onBackClick = { /* Handle top bar button click */ },
                navController = navController
            )
        },
        bottomBar = {
            AppNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("input") }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { padding ->
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize().verticalScroll(rememberScrollState())
                .padding(padding),
        ) {
            repeat(40) {
                Text(
                    text = "Index $it"
                )
            }
        }
    }
}

