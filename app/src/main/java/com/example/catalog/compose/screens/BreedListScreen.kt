package com.example.catalog.compose.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    eventPublisher: (BreedListUiEvent) -> Unit,
    onClick: (Breed) -> Unit
) {
    val logo: Painter = painterResource(id = R.drawable.logo_vector)
    val searchText by remember { mutableStateOf(state.filter) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Scaffold (
        topBar = {
            CustomTopBar(
                logo = logo,
                searchText = remember { mutableStateOf(searchText) },
                eventPublisher = eventPublisher,
                keyboardController = keyboardController,
                focusManager = focusManager
            )
        },
        content = {
            BreedList(
                items = state.currentBreeds,
                padding = it,
                onClick = onClick
            )
            DisplayEmptyStateOrError(state)
        }
    )
}

// Components
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTopBar(
    logo: Painter,
    searchText: MutableState<String>,
    eventPublisher: (BreedListUiEvent) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    Column {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Catalog")
            },
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
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = searchText.value,
            onValueChange = {
                searchText.value = it
                eventPublisher(BreedListUiEvent.SearchChanged(it))
            },
            label = { Text("Search") },
            keyboardActions = KeyboardActions(onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                if (searchText.value.isNotEmpty()) {
                    IconButton(onClick = {
                        eventPublisher(BreedListUiEvent.SearchChanged(""))
                        searchText.value = ""
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = "Clear text")
                    }
                }
            }
        )
        Divider()
    }
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

@Composable
private fun DisplayEmptyStateOrError(state: BreedListState) {
    if (state.currentBreeds.isEmpty()) {
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

// Navigation
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.breedsListScreen(
    route: String,
    navController: NavController,
) = composable (route = route) {

    val breedListViewModel = viewModel<BreedListViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return BreedListViewModel(BreedRepository) as T
            }
        }
    )

    val state by breedListViewModel.state.collectAsState()

    BreedListScreen(
        state = state,
        eventPublisher = {
            breedListViewModel.setEvent(it)
        },
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
            eventPublisher = {},
            onClick = {},
        )
    }
}