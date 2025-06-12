package com.example.watchlist
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun SettingsScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {

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
            CardWithSwitch(
                isDarkTheme = isDarkTheme,
                onThemeChange = onThemeChange
            )
        }
    }
}


@Composable
fun CardWithSwitch(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Dark Mode/Light Mode",
                fontSize = 24.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier.padding(start = 16.dp)
            )
            Switch(
                checked = isDarkTheme,
                onCheckedChange = onThemeChange,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
    }
}

//TO DO:
//1.naprawić ten holerny błąd który sprawia że wszystko się zamyka po kliknięciu
//w przełącznik
//2. stworzyć mechanizm sprawdzający i pamiętający czy hasło jest uruchomione