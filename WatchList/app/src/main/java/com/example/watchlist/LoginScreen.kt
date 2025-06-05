package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome Traveler", fontSize = 35.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "ENTER YOUR PASSWORD", fontSize = 24.sp, fontWeight = FontWeight.W300)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = {
            Text(text = "PASSWORD")
        })
        Spacer(modifier = Modifier.height(16.dp))
        // TO DO: uzupełnić co guziczek ma robić
        Button(onClick = {}, modifier = Modifier
            .width((250.dp))
            .height(56.dp)                // zwiększona wysokość
            .padding(horizontal = 32.dp)
        ) {
            Text(text = "LOGIN", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

