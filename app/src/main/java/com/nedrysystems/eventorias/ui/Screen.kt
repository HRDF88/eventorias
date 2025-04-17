package com.nedrysystems.eventorias.ui

sealed class Screen(
    val route: String
) {
    data object Home : Screen(route = "home")

    data object Event : Screen(route = "event")

    data object Profile : Screen(route = "profile")

    data object  Login : Screen(route = "login")


}