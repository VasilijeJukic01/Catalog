package com.example.catalog.compose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.catalog.R
import com.example.catalog.compose.*
import com.example.catalog.model.*
import com.example.catalog.model.list.*
import com.example.catalog.repo.*
import com.example.catalog.ui.theme.*

@ExperimentalMaterial3Api
@Composable
fun BreedListScreen(
    state : BreedListState,
    onClick: (Breed) -> Unit
) {
    val logo: Painter = painterResource(id = R.drawable.logo_vector)

    Scaffold (
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = { Text(text = "Catalog") },
                    navigationIcon = {
                        Image(
                            painter = logo,
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .size(48.dp)
                        )
                    }
                )
                Divider()
            }
        },
        content = {
            BreedList(
                items = state.breeds,
                padding = it,
                onClick = onClick
            )

            if (state.breeds.isEmpty()) {
                when (state.fetching) {
                    true -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    false -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if (state.error == null) "No breeds found" else "Error: ${state.error}"
                            )
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun BreedList(
    items: List<Breed>,
    padding: PaddingValues,
    onClick: (Breed) -> Unit
) {
    val scrollState = rememberScrollState()

    Column (
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        items.forEach {
            Column {
                key(it.id) {
                    BreedCard(
                        breed = it,
                        onClick = { onClick(it) },
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}

// Navigation
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.breedsListScreen(
    route: String,
    navController: NavController,
) = composable (route = route) {

    val breedListViewModel = viewModel<BreedListViewModel>()
    val state by breedListViewModel.state.collectAsState()

    BreedListScreen(
        state = state,
        onClick = { breed ->
            navController.navigate(route = "breeds/${breed.id}")
        },
    )
}

// Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewCatListScreen() {
    CatalogTheme {
        BreedListScreen(
            state = BreedListState(breeds = DataSample),
            onClick = {},
        )
    }
}