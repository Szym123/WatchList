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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.TextFieldValue
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
                        CardInList(userViewModel, user, navController)
                    }
                }
            }
        }
    }
}

@Composable
fun CardInList(userViewModel: UserViewModel, user: User, navController: NavHostController) {
    var isLike by remember { mutableStateOf<Boolean>(user.isLike) }

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
                    .clickable{
                        navController.currentBackStackEntry?.savedStateHandle?.set("id", user.id.toString())
                        navController.navigate("input")
                    },
                colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
            ) {
                AsyncImage(
                    model = Uri.parse(user.image),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize(), // Image fills the card
                    contentScale = ContentScale.Crop // Crop to fill the bounds
                )
            }
            Column(
                modifier = Modifier
                    .weight(4f)
                    .padding(8.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    user.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    user.id.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                if (isLike) {
                    IconButton(onClick = {
                        user.isLike = false
                        isLike = false
                        userViewModel.update(user)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Is Liked"
                        )
                    }
                } else {
                    IconButton(onClick = {
                        user.isLike = true
                        isLike = true
                        userViewModel.update(user)
                    }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Is not Liked"
                        )
                    }
                }
            }
        }
    }
}