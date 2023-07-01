package com.example.codeglamour.util.classes

data class Message(
    val senderEmail: String,
    val content: String,
    val timestamp: com.google.firebase.Timestamp
)
