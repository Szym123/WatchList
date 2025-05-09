package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController, userViewModelFactory: UserViewModelFactory) {
    val userViewModel: UserViewModel = viewModel(factory = userViewModelFactory)

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
                CardInList()
            }
        }
    }
}

@Composable
fun CardInList() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ){
            Text("XD")
            Column{
                Text("Name")
                Text("More info")
            }
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Add"
            )
        }
    }
}