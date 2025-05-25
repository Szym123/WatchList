package com.example.watchlist

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun MainScreen(navController: NavHostController, userViewModel: UserViewModel) {
    val users by userViewModel.allUsers.observeAsState(emptyList())
    val listState = rememberLazyListState()

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
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (users.isEmpty()) {
                Card(
                    modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
                ){
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("No movies added", style = MaterialTheme.typography.titleLarge)
                    }
                }

            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(users) { user ->
                        CardInList(user,navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CardInList(user: User, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
            .fillMaxSize(),
            horizontalArrangement = Arrangement.End
        ){
            Card(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
                    .clickable{ navController.navigate("input") },
            ) {
                if (Uri.parse(user.image) == null) {
                    Text("No")
                } else {
                    AsyncImage(
                        model = Uri.parse(user.image),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxSize(), // Image fills the card
                        contentScale = ContentScale.Crop // Crop to fill the bounds
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ){
                Text(user.name, style = MaterialTheme.typography.titleMedium)
                Text(user.additionalInfo, style = MaterialTheme.typography.bodyMedium)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                if (user.isLike) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Is Liked"
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Is not Liked"
                    )
                }
            }
        }
    }
}