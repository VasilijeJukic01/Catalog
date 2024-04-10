package com.example.catalog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.catalog.compose.screens.breedDetailsScreen
import com.example.catalog.compose.screens.breedsListScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breeds",
    ) {
        // Routes
        breedsListScreen (
            route = "breeds",
            navController = navController,
        )
        breedDetailsScreen(
            route = "breeds/{id}",
            navController = navController,
        )
    }

}