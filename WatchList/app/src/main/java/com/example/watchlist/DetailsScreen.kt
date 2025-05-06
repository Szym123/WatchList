package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun DetailsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = true,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            AppNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("input") }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Add"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ){
            Text("Image")
            Text("Name")
            Text("SubName")
            Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam eu risus justo. Ut viverra mi quis nunc suscipit, quis eleifend eros condimentum. Nunc faucibus nulla dolor, et consectetur eros porttitor a. Donec gravida, mauris eu condimentum malesuada, lectus dolor lacinia nunc, ut semper lacus tellus ut dolor. Vestibulum id ultricies lacus. Aenean vitae consequat lectus. Sed ac aliquet nulla.")
        }
    }
}