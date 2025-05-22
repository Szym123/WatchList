package com.example.watchlist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember

@Composable
fun InputScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var name by remember { mutableStateOf(TextFieldValue("")) }
    var additionalInfo by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var video by remember { mutableStateOf(TextFieldValue("")) }
    var image by remember { mutableStateOf(TextFieldValue("")) }

    val newUser = User(
        name = name.text,
        additionalInfo = additionalInfo.text,
        description = description.text,
        isLike = false,
        video = video.text,
        image = image.text
    )

    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = true,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
                floatingActionButton = {
            FloatingActionButton(onClick = { userViewModel.insertUser(newUser) }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            CardWithPhoto()
            SimpleFilledTextFieldSample("Add title",70,value = name,onValueChange = { name = it })
            SimpleFilledTextFieldSample("Add subtitle",70,value = additionalInfo,onValueChange = { additionalInfo = it })
            SimpleFilledTextFieldSample("Add descryption",175,value = description,onValueChange = { description = it })
            SimpleFilledTextFieldSample("Add link to video",70,value = video,onValueChange = { video = it })
        }
    }
}

@Composable
fun SimpleFilledTextFieldSample(
    descryption: String,
    height: Int,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(5.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(descryption) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun CardWithPhoto() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp)
    ) {
        Text("Name")

    }
}