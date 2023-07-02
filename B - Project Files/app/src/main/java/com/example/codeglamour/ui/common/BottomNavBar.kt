package com.example.codeglamour.ui.common

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.codeglamour.util.classes.Screens

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    androidx.compose.material3.BottomAppBar(
        tonalElevation = 5.dp,
        containerColor = MaterialTheme.colorScheme.primary,
        actions = {
            IconButton(onClick = {navController.navigate(Screens.ProfileScreen.route)}) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "", tint = Color.White)
            }
            IconButton(onClick = {navController.navigate(Screens.ChatListScreen.route)}) {
                Icon(
                    Icons.Filled.Email, contentDescription = "", tint = Color.White)
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Settings, contentDescription = "", tint = Color.White,)
            }
        },
        floatingActionButton = {
            androidx.compose.material3.FloatingActionButton(
                onClick = { navController.navigate(Screens.AddExhibitScreen.route) },
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    )
}