package com.example.codeglamour.ui.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.codeglamour.util.classes.Message
import com.example.codeglamour.util.functions.chats.fetchMessages
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sendMessage


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatScreenView(
    recipientEmail: String,
) {
    val currentUserEmail = FirebaseAuth.getInstance().currentUser!!.email
    var messages = rememberFetchMessages(currentUserEmail!!, recipientEmail)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = recipientEmail, textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        },
        bottomBar = {
            SendMessageField(
                onSendMessage = { messageContent ->
                    sendMessage(currentUserEmail, recipientEmail, messageContent)
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                MessagesList(
                    messages = messages.value,
                    currentUserEmail = currentUserEmail
                )
            }
        }
    )
}

@Composable
fun MessagesList(
    messages: List<Message>,
    currentUserEmail: String,
) {
    LazyColumn(
        modifier = Modifier.padding(top = 70.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(messages.size) { index ->
            val isCurrentUser = messages[index].senderEmail == currentUserEmail

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .shadow(elevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = messages[index].content,
                            color = Color.Black,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageField(onSendMessage: suspend (String) -> Unit) {
    var messageText by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = { messageText = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text(text = "Type a message") }
        )
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    onSendMessage(messageText)
                }
                messageText = ""
            },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = "Send")
        }
    }
}

@Composable
fun rememberFetchMessages(
    currentUserEmail: String,
    recipientEmail: String,
): State<MutableList<Message>> {
    val messagesState = remember { mutableStateOf(emptyList<Message>().toMutableList()) }

    LaunchedEffect(Unit) {
        try {
            val messages = fetchMessages(currentUserEmail, recipientEmail)
            messagesState.value = messages
        } catch (e: Exception) {
            Log.d("Error", e.message!!)
        }

    }

    return messagesState
}

