package com.example.codeglamour.util.functions

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

// Function to fetch image URLs from Firebase Storage
suspend fun fetchImageUrls(): List<String> = withContext(Dispatchers.IO) {
    val storage = Firebase.storage
    val imagesRef = storage.reference.child("items")

    val result = mutableListOf<String>()
    try {
        val items = imagesRef.listAll().await()
        items.items.forEach { item ->
            val url = item.downloadUrl.await().toString()
            result.add(url)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return@withContext result
}
