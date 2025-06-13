package com.example.watchlist
import android.widget.Toast
import androidx.compose.foundation.layout.*

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun SettingsScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    isDarkTheme: Boolean,
    authViewModel: AuthViewModel,
    onThemeChange: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val userCredentials by authViewModel.getCredentialsFlow().collectAsState(initial = null)

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
                onClick = {
                    coroutineScope.launch {
                        if (userCredentials?.enabled == true) {
                            // HASŁO JEST AKTYWNE -> DEZAKTYWUJ
                            authViewModel.updateCredentials(
                                userCredentials!!.copy(enabled = false, passwordHash = null)
                            )
                            Toast.makeText(context, "Pass deactivated", Toast.LENGTH_SHORT).show()
                        } else {
                            // HASŁO NIEAKTYWNE -> AKTYWUJ I PRZEJDŹ DO USTAWIENIA
                            // Najpierw ustaw enabled na true, hash na null, aby system wiedział, że trzeba go ustawić
                            authViewModel.insertOrUpdateCredentials(enabled = true, passwordHash = null)
                            navController.navigate("password")
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
            ) {
                val buttonText = when {
                    userCredentials?.enabled == true -> "Dezactivate pass"
                    else -> "Aktywuj hasło"
                }
                Text(buttonText)
            }
            // Ten przycisk "Change Password" jest widoczny tylko, gdy hasło jest enabled i ma hash (czyli nie jest null)
            if (userCredentials?.enabled == true && userCredentials?.passwordHash != null) {
                Button(
                    onClick = {
                        navController.navigate("password")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .padding(top = 5.dp)
                ) {
                    Text("Change Password")
                }
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

