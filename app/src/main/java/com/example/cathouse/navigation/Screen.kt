package com.example.cathouse.navigation

sealed class Screen(val route: String) {
    data object CatsScreen : Screen("cats_screen")
}