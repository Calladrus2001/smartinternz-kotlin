package com.example.codeglamour.util.functions.chats

import com.example.codeglamour.util.classes.Message
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

suspend fun fetchMessages(
    currentUserEmail: String,
    recipientEmail: String
): MutableList<Message> = withContext(Dispatchers.IO) {
    val messages = mutableListOf<Message>()

    val chatSnapshot = Firebase.firestore
        .collection("chats")
        .whereArrayContainsAny("participants", listOf(currentUserEmail, recipientEmail))
        .limit(1)
        .get()
        .await()

    val chatDocument = chatSnapshot.documents.firstOrNull()

    chatDocument?.let { chat ->
        val messagesSnapshot = chat.reference
            .collection("messages")
            .orderBy("timestamp")
            .get()
            .await()

        for (messageDocument in messagesSnapshot.documents) {
            val sender = messageDocument.getString("sender")
            val content = messageDocument.getString("content")
            val timestamp = messageDocument.getTimestamp("timestamp")

            if (sender != null && content != null && timestamp != null) {
                val message = Message(sender, content, timestamp)
                messages.add(message)
            }
        }
    }

    messages
}