package com.example.watchlist

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

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
                    Text(
                        text="NO"
                    )
                }

            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(users) { user ->
                        CardInList(user = user)
                    }
                }
            }
        }
    }
}

@Composable
fun CardInList(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
    ) {
        Row(
            modifier = Modifier
            .fillMaxSize()
        ){
            Text("XD")
            Column{
                Text(user.name)
                Text(user.additionalInfo)
            }
            if (user.isLike) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Is Liked"
                )
            } else{
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Is not Liked"
                )
            }
        }
    }
}