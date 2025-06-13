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
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var passwordInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = false,
                onBackClick = { /* No-op */ },
                navController = navController
            )
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
            Text(
                text = "Hello There",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = passwordInput,
                onValueChange = { passwordInput = it },
                label = { Text("General Kenobi") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                        if (passwordInput.isBlank()) {
                            errorMessage = "Where is Padme?"
                            return@launch
                        }

                        val credentials = authViewModel.getCredentials()
                        if (credentials != null && credentials.passwordHash != null) {
                            val isCorrect = authViewModel.checkPassword(passwordInput, credentials.passwordHash!!)
                            if (isCorrect) {
                                Toast.makeText(context, "May the Force be with You", Toast.LENGTH_SHORT).show()
                                navController.navigate("main") {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                }
                            } else {
                                errorMessage = "These are not droids we are looking for"
                            }
                        } else {
                            errorMessage = "There is no key to this puzzle"
                            navController.navigate("main") {
                                popUpTo(navController.graph.id) { inclusive = true }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("GO!")
            }
        }
    }
}
