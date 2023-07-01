package com.example.codeglamour.util.classes

sealed class Screens(val route: String) {
    object AuthScreen : Screens("auth_screen")
    object MainScreen : Screens("main_screen")
    object ChatListScreen : Screens("chat_list_screen")
    object AddExhibitScreen : Screens("add_exhibit_screen")
    object ProfileScreen : Screens("profile_screen")
    object ChatScreen : Screens("chat_screen/{email}") {
        const val ARG_EMAIL = "email"
        fun routeWithArgs(email: String): String = "chat_screen/$email"
    }
}
