package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun InputScreen(navController: NavHostController, userViewModel: UserViewModel) {

    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = true,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            BottomAppBar()
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            CardWithPhoto()
            SimpleFilledTextFieldSample("Add title",70)
            SimpleFilledTextFieldSample("Add subtitle",70)
            SimpleFilledTextFieldSample("Add descryption",175)
        }
    }
}

@Composable
fun SimpleFilledTextFieldSample(descryption: String, height: Int) {
    var text by remember { mutableStateOf("") }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(5.dp),
        value = text,
        onValueChange = { text = it },
        label = { Text(descryption) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun CardWithPhoto() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp)
    ) {
        Text("Name")
        FloatingActionButton(
            modifier = Modifier
                .padding(5.dp),
            onClick = {  }
        ){
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}

@Composable
fun BottomAppBar() {
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