package com.example.codeglamour.util.functions.chats

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

suspend fun fetchChatsForUser(email: String): List<DocumentSnapshot> = withContext(Dispatchers.IO) {
    val firestore = FirebaseFirestore.getInstance()
    val chatsCollection = firestore.collection("chats")

    val query = chatsCollection.whereArrayContains("participants", email)
    val querySnapshot = query.get().await()

    querySnapshot.documents
}
