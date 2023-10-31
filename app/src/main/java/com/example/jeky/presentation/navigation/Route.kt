package com.example.jeky.presentation.navigation

sealed class Route(val route: String) {
    object Login : Route("Login")
    object Register : Route("Register")
    object Home : Route("Home")
}
