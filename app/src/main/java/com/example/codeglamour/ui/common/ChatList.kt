package com.example.codeglamour.ui.common

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.codeglamour.util.classes.Screens
import com.example.codeglamour.util.functions.chats.fetchChatsForUser
import com.google.firebase.firestore.DocumentSnapshot

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatList(email:String, navController: NavController) {
    val chats = rememberFetchChats(email)
    val emailInput = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog.value = true },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Start Chat"
                )
            }
        }
    ) {
        LazyColumn {
            items(chats.value.size) { index ->
                ChatCard(chats.value[index], email, navController)
            }
        }

        if (showDialog.value) {
            ChatDialog(
                emailInput = emailInput.value,
                onEmailInputChanged = { emailInput.value = it },
                onDialogDismissed = { showDialog.value = false },
                onDialogSubmit = { newEmail ->
                    navController.navigate("${Screens.ChatScreen.route}/${newEmail}")
                }
            )
        }
    }
}
@Composable
fun ChatCard(chat: DocumentSnapshot, email: String, navController: NavController) {
    val participants = chat.get("participants") as List<String>
    val otherParticipant = participants.find { it != email }
    val chatTitle = otherParticipant?.let { getChatTitleForUser(it) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                try {
                    navController.navigate("${Screens.ChatScreen.route}/${otherParticipant}")
                } catch (e: Exception) {
                    Log.d("Error", e.message!!)
                }

            },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            if (chatTitle != null) {
                Text(
                    text = chatTitle,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            // Additional chat details or preview can be added here
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDialog(
    emailInput: String,
    onEmailInputChanged: (String) -> Unit,
    onDialogDismissed: () -> Unit,
    onDialogSubmit: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDialogDismissed,
        title = { Text(text = "Enter Email ID") },
        text = {
            TextField(
                value = emailInput,
                onValueChange = onEmailInputChanged,
                label = { Text(text = "Email ID") }
            )
        },
        confirmButton = {
            Button(
                onClick = { onDialogSubmit(emailInput) }
            ) {
                Text(text = "Submit")
            }
        },
        dismissButton = {
            Button(
                onClick = onDialogDismissed
            ) {
                Text(text = "Cancel")
            }
        }
    )
}

@Composable
fun rememberFetchChats(email: String): State<List<DocumentSnapshot>> {
    val chatsState = remember { mutableStateOf(emptyList<DocumentSnapshot>()) }

    LaunchedEffect(Unit) {
        val chats = fetchChatsForUser(email)
        chatsState.value = chats
    }

    return chatsState
}

fun getChatTitleForUser(participantEmail: String): String {
    return participantEmail
}
