package com.example.watchlist

import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun SettingsScreen(navController: NavHostController, userViewModel: UserViewModel) {
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ){
            Button(
                onClick = { navController.navigate("password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                Text("Change the password")
            }
            Button(
                onClick = { userViewModel.deleteAll() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(5.dp)
            ) {
                Text("Delete all users")
            }
            CardWithSwitch()
        }
    }
}

@Composable
fun CardWithSwitch() {
    val navController = rememberNavController()
    var checked by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    if (it) { // tylko gdy przełącznik jest włączany (checked = true)
                        navController.navigate("password") // zastąp swoją trasą docelową
                    }
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "ENABLE YOUR PASSWORD",
                fontSize = 24.sp,
                fontWeight = FontWeight.W500
            )
        }
    }
}

//TO DO:
//1.naprawić ten holerny błąd który sprawia że wszystko się zamyka po kliknięciu
//w przełącznik
//2. stworzyć mechanizm sprawdzający i pamiętający czy hasło jest uruchomione