package com.example.cathouse.navigation

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cathouse.features.cats.layers.presenter.route.CatListDetailsRout

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun Navigation(
    windowSizeClass: WindowSizeClass = calculateWindowSizeClass(LocalContext.current as Activity)
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.CatsScreen.route
    ) {
        composable(route = Screen.CatsScreen.route) {
            CatListDetailsRout(windowSizeClass)
        }
    }
}