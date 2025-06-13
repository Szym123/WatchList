package com.example.watchlist

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun PasswordScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current // toasts->short pop ups for short time on the screen
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
                .padding(padding)
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmNewPassword,
                onValueChange = { confirmNewPassword = it },
                label = { Text("Confirm New Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    coroutineScope.launch {
                        errorMessage = null

                        if (newPassword.isBlank() || confirmNewPassword.isBlank()) {
                            errorMessage = "Wait Bro, gimme some signes"
                            return@launch
                        }
                        if (newPassword != confirmNewPassword) {
                            errorMessage = "You have one password, I have other... We are not the same"
                            return@launch
                        }
                        if (newPassword.length < 4) {
                            errorMessage = "I WANT MORE SIGNES! GIMME OR GO AWAY!"
                            return@launch
                        }
                        try {
                            val hashedPassword = authViewModel.hashPassword(newPassword)

                            if (hashedPassword != null) {
                                    authViewModel.insertOrUpdateCredentials(enabled = true, passwordHash = hashedPassword)
                                 } else {
                                    errorMessage = "Failed to hash password."
                                 }

                            Toast.makeText(context, "Everything is ok", Toast.LENGTH_SHORT).show()
                            navController.navigate("main") {
                                popUpTo("password") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            errorMessage = "Those are not droids we are looking for (You got errr Bro): ${e.message}"
                            e.printStackTrace()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("BE SECURE")
            }
        }
    }
}
