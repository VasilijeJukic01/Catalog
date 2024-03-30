package com.example.catalog.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.catalog.R
import com.example.catalog.compose.BreedCard
import com.example.catalog.model.Breed
import com.example.catalog.repo.DataSample
import com.example.catalog.ui.theme.CatalogTheme

@ExperimentalMaterial3Api
@Composable
fun BreedListScreen(
    items: List<Breed>,
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
            val scrollState = rememberScrollState()

            Column (
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(it),
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
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewCatListScreen() {
    CatalogTheme {
        BreedListScreen(
            items = DataSample,
            onClick = {},
        )
    }
}