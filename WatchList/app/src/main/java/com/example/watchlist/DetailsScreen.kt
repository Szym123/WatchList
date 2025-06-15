import com.example.watchlist.AppTopBar
import com.example.watchlist.CardWithPhoto
import com.example.watchlist.UserViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.core.net.toUri
import com.example.watchlist.LiveTvScreen

@Composable
fun DetailsScreen(navController: NavHostController, userViewModel: UserViewModel) {
    var id: Int? = null
    navController.previousBackStackEntry?.savedStateHandle?.get<String>(key = "id")?.let {
        id = it.toInt()
    }


    val users by userViewModel.allUsers.observeAsState(emptyList())

    val user = users[id!!]
    val displayName = user.name
    val displayAdditionalInfo = user.additionalInfo
    val displayDescription = user.description
    val displayVideo = user.video
    val displayImageUri = user.image!!.toUri()


    Scaffold(
        topBar = {
            AppTopBar(
                showBackArrow = true,
                onBackClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = { },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("id", (user.id.toInt()-1).toString())
                    navController.navigate("input")
                }
            ) {
                Icon(Icons.Filled.Edit, "Edit")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            CardWithPhoto(displayImageUri)
            DisplayItem("Title", displayName)
            DisplayItem("Subtitle", displayAdditionalInfo)
            DisplayItem("Descryption", displayDescription)

            LiveTvScreen(displayVideo.toString())
        }
    }
}

@Composable
fun DisplayItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
