package com.example.catalog.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.catalog.compose.NoDataContent
import com.example.catalog.model.Breed
import com.example.catalog.model.Characteristics
import com.example.catalog.model.details.BreedDetailsState
import com.example.catalog.model.details.BreedDetailsViewModel
import com.example.catalog.repo.BreedRepository
import com.example.catalog.ui.theme.topBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailsScreen(
    state: BreedDetailsState,
    onBackClick: () -> Unit
) {
    Surface {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Details",
                            color = Color.Black,
                            modifier = Modifier.padding(end = 42.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (state.fetching) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
            }
            else if (state.error != null) {
                Text(
                    text = "Error: ${state.error}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            else if (state.data != null) {
                BreedDataLazyColumn(data = state.data)
            }
            else {
                NoDataContent(id = state.breedId)
            }
        }
    }
}

@Composable
fun BreedDataLazyColumn(
    data: Breed
) {
    LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
        item {
            // Name
            Text(
                style = MaterialTheme.typography.headlineLarge,
                text = data.name
            )

            // Alt Names
            Text(
                style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                text = "Also known as: " + data.altNames.joinToString(", ")
            )
            // TODO: Load image from URL
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(style = MaterialTheme.typography.bodyLarge, text = data.description)
            Spacer(modifier = Modifier.height(16.dp))

            // Origin
            Row {
                Text(
                    text = "Origin: ",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = data.origin,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Temperament
            Row {
                Text(
                    text = "Temperament: ",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = data.temperament.joinToString(", "),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Life Span
            Row {
                Text(
                    text = "Life Span: ",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = data.lifeSpan,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Weight
            Row {
                Text(
                    text = "Weight: ",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = data.weight,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Characteristics
            BreedCharacteristicBar(
                characteristicName = "Adaptability",
                characteristicValue = data.characteristics.adaptability
            )
            BreedCharacteristicBar(
                characteristicName = "Affection Level",
                characteristicValue = data.characteristics.affectionLevel
            )
            BreedCharacteristicBar(
                characteristicName = "Energy Level",
                characteristicValue = data.characteristics.energyLevel
            )
            BreedCharacteristicBar(
                characteristicName = "Intelligence",
                characteristicValue = data.characteristics.intelligence
            )
            BreedCharacteristicBar(
                characteristicName = "Stranger Friendly",
                characteristicValue = data.characteristics.strangerFriendly
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Rare
            Row {
                Text(
                    text = "Rare: ",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = if (data.rare == 1) "Yes" else "No",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Wikipedia
            Button(
                onClick = { /*TODO: Implement onClick*/ },
                shape = RectangleShape,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
            ) {
                Text("Open Wikipedia Page")
            }
        }
    }
}

@Composable
fun BreedCharacteristicBar(characteristicName: String, characteristicValue: Int) {
    Column {
        Text(text = characteristicName, style = MaterialTheme.typography.bodyLarge)
        LinearProgressIndicator(
            progress = characteristicValue / 5f,
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

fun NavGraphBuilder.breedDetailsScreen(
    route: String,
    navController: NavController,
) = composable(
    route = route
) { navBackStackEntry ->
    val dataId = navBackStackEntry.arguments?.getString("id")
        ?: throw IllegalArgumentException("id is required.")

    val breedDetailsViewModel = viewModel<BreedDetailsViewModel>(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return BreedDetailsViewModel(breedId = dataId) as T
            }
        }
    )

    val state = breedDetailsViewModel.state.collectAsState()

    BreedDetailsScreen(
        state = state.value,
        onBackClick = {
            navController.popBackStack()
        }
    )
}

@Preview
@Composable
fun PreviewDetailsScreen() {
    Surface {
        BreedDetailsScreen(
            state = BreedDetailsState(
                breedId = "1",
                data = Breed(
                    id = "2",
                    name = "Siamese",
                    altNames = listOf("Siam"),
                    description = "The Siamese cat is one of the first distinctly recognized breeds of Asian cat.",
                    temperament = listOf("Active", "Playful", "Intelligent", "Affectionate"),
                    origin = "Thailand",
                    weight = "8-15 pounds",
                    lifeSpan = "10-15 years",
                    rare = 0,
                    characteristics = Characteristics(
                        adaptability = 5,
                        affectionLevel = 5,
                        energyLevel = 4,
                        intelligence = 5,
                        strangerFriendly = 3
                    ),
                    wikipediaUrl = "https://en.wikipedia.org/wiki/Siamese_cat"
                ),
            ),
            onBackClick = {}
        )
    }

}