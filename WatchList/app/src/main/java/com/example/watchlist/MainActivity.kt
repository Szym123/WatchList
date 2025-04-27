package com.example.watchlist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watchlist.ui.theme.WatchListTheme
import androidx.compose.animation.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items


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
                                colors = TopAppBarDefaults.topAppBarColors(Color.Magenta),
                                title = {
                                    Text(
                                        "WatchList",
                                        maxLines = 1,
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { /* do something */ }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.List,
                                            contentDescription = "Localized description"
                                        )
                                    }
                                },
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(onClick = { /* Handle FAB click */ }) {
                                Icon(Icons.Filled.Add, contentDescription = "add")
                            }
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) {
                        ListAnimatedItems(items = listOf("Germany","India","Japan","Brazil","Australia"))
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

@Composable
fun ListAnimatedItems(
     items: List<String>,
 modifier: Modifier = Modifier
) {
     LazyColumn(modifier) {
         items(items, key = { it }) { item ->
            ListItem(
            headlineContent = { Text(item) },
            modifier = Modifier
            .animateItem(
                // Optionally add custom animation specs
            )
            .fillParentMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
         )
         }
     }
}
