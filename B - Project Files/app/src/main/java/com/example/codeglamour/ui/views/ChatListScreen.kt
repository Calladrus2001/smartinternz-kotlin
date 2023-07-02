package com.example.codeglamour.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.codeglamour.ui.common.ChatList
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ChatListScreen(
    navController: NavController
) {
    val email = FirebaseAuth.getInstance().currentUser!!.email
    ChatList(email = email!!, navController = navController)
}