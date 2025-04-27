package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchlist.ui.theme.WatchListTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchListTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        topBar = {
                            CenterAlignedTopAppBar(
                                title = { Text(
                                    "Centered Top App Bar",
                                    maxLines = 1,
                                ) })
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                                Icon(Icons.Filled.Menu, contentDescription = "menu")
                            }
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) {
                        SimpleUI()
                    }
                }
            }
        }
    }
}

@Composable
fun SimpleUI() {
    var text by remember { mutableStateOf("Hello, Jetpack Compose!") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = text, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { text = "Button Clicked!" }) {
            Text("Click Me")
        }
    }
}
