package com.example.catalog.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.catalog.compose.screens.BreedDetailsScreen
import com.example.catalog.compose.screens.BreedListScreen
import com.example.catalog.repo.DataRepo

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "breeds",
    ) {
        breedsListScreen (
            route = "breeds",
            navController = navController,
        )

        composable(
            route = "breeds/{id}",
        ) { navBackStackEntry ->
            val dataId = navBackStackEntry.arguments?.getString("id")
                ?: throw IllegalArgumentException("id is required.")

            val data = DataRepo.getById(id = dataId)

            if (data != null) {
                BreedDetailsScreen(
                    data = data,
                    onBackClick = {
                        navController.popBackStack()
                    }
                )
            } else {
                NoDataContent(id = dataId)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
private fun NavGraphBuilder.breedsListScreen(
    route: String,
    navController: NavController,
) {
    composable(route = route) {
        BreedListScreen(
            items = DataRepo.allBreeds(),
            onClick = { breed ->
                navController.navigate(route = "breeds/${breed.id}")
            },
        )
    }
}

@Composable
private fun NoDataContent(
    id: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "There is no data for id '$id'.",
            fontSize = 18.sp,
        )
    }
}