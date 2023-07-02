package com.example.codeglamour

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.codeglamour.ui.views.AddExhibitScreenView
import com.example.codeglamour.ui.views.AuthScreenView
import com.example.codeglamour.ui.views.ChatListScreen
import com.example.codeglamour.ui.views.ChatScreenView
import com.example.codeglamour.ui.views.MainScreenView
import com.example.codeglamour.ui.views.ProfileScreenView
import com.example.codeglamour.util.classes.Screens
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            Navigation()
        }
    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        val _startDestination = Screens.AuthScreen.route
        NavHost(navController = navController, startDestination = _startDestination) {
            composable(route = Screens.AuthScreen.route) {
                AuthScreenView(navController = navController, auth = auth)
            }
            composable(route = Screens.MainScreen.route) {
                MainScreenView(navController = navController)
            }
            composable(route = Screens.ChatListScreen.route) {
                ChatListScreen(navController = navController)
            }
            composable(route = Screens.AddExhibitScreen.route) {
                AddExhibitScreenView(navController = navController)
            }
            composable(route = Screens.ProfileScreen.route) {
                ProfileScreenView(navController = navController)
            }
            composable(
                route = "${Screens.ChatScreen.route}/{${Screens.ChatScreen.ARG_EMAIL}}",
                arguments = listOf(navArgument(Screens.ChatScreen.ARG_EMAIL) { type = NavType.StringType })
            ) { backStackEntry ->
                val email = backStackEntry.arguments?.getString(Screens.ChatScreen.ARG_EMAIL)
                email?.let { recipientEmail ->
                    ChatScreenView(recipientEmail)
                }
            }
        }
    }
}