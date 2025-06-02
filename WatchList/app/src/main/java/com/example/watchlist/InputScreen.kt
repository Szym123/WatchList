package com.example.watchlist

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.runtime.LaunchedEffect
import androidx.core.net.toUri

@Composable
fun InputScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var id: Int? = null
    navController.previousBackStackEntry?.savedStateHandle?.get<String>(key = "id")?.let {
        id = it.toInt()-1
    }

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var additionalInfo by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var video by remember { mutableStateOf(TextFieldValue("")) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

// Observe all users and update fields if an ID is present
    val users by userViewModel.allUsers.observeAsState(emptyList())

// Use LaunchedEffect to react to changes in 'id' and 'users'
    LaunchedEffect(id, users) {
        if (id != null && id!! >= 0 && id!! < users.size) {
            val user = users[id!!]
            name = TextFieldValue(user.name)
            additionalInfo = TextFieldValue(user.additionalInfo)
            description = TextFieldValue(user.description)
            video = TextFieldValue(user.video)
            imageUri = user.image!!.toUri()
        } else {
            name = TextFieldValue("")
            additionalInfo = TextFieldValue("")
            description = TextFieldValue("")
            video = TextFieldValue("")
            imageUri = null
        }
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                imageUri = uri
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    )

    val newUser = User(
        name = name.text,
        additionalInfo = additionalInfo.text,
        description = description.text,
        isLike = false,
        video = video.text,
        image = imageUri.toString()
    )

    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = true,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = { BottomAppBarEx(
            userViewModel,
            newUser,
            id,
            navController,
            ){photoPickerLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))}
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            CardWithPhoto(imageUri)
            Text(
                id.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
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
fun CardWithPhoto(imageUri: Uri?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imageUri == null) {
                Text("No image selected", style = MaterialTheme.typography.titleLarge)
            } else {
                AsyncImage(
                    model = imageUri,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize(), // Image fills the card
                    contentScale = ContentScale.Crop // Crop to fill the bounds
                )
            }
        }
    }
}

@Composable
fun BottomAppBarEx(userViewModel: UserViewModel, newUser: User, id: Int?, navController: NavHostController, onClick: () -> Unit) {
    BottomAppBar(
        actions = {
            IconButton(onClick = {
                userViewModel.insertUser(newUser)
                navController.popBackStack()
            }) {
                Icon(Icons.Filled.Check, contentDescription = "Save")
            }
            IconButton(onClick = {
                if (id != null) {
                    userViewModel.deleteUser(id+1)
                    navController.popBackStack()
                }
            }) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "Delete",
                )
            }
            IconButton(onClick = onClick) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "Add image",
                )
            }
        }
    )
}