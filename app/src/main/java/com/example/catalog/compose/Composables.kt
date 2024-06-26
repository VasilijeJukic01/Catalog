package com.example.catalog.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.catalog.model.Breed
import com.example.catalog.repo.DataSample
import com.example.catalog.ui.theme.CatalogTheme
import com.example.catalog.ui.theme.cardColor
import androidx.compose.ui.unit.*

@Composable
fun BreedCard(
    breed: Breed,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        // Name
        Row {
            Text(
                text = breed.name,
                modifier = Modifier
                    .padding()
                    .padding(10.dp),
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.Bold
            )
        }

        // Alternative names
        Row {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 12.dp)
                    .weight(weight = 1f),
                fontSize = 12.sp,
                text = buildAnnotatedString {
                    append("Also known as: ")
                    withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(breed.altNames.joinToString(", "))
                    }
                }
            )
        }

        // Description
        Row {
            Text(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(weight = 1f),
                fontSize = 12.sp,
                text = if (breed.description.length > 250) {
                    "${breed.description.take(250)}..."
                } else {
                    breed.description
                },
                lineHeight = 16.sp
            )
        }

        // Temperament
        Row {
            breed.temperament.take(3).forEach { temperament ->
                SuggestionChip(
                    onClick = {},
                    modifier = Modifier.padding(4.dp),
                    label  = { Text(temperament) }
                )
            }
        }

        // Arrow icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .padding(end = 5.dp),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }
    }
}

@Composable
fun NoDataContent(
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

// Preview
@Preview(showBackground = true)
@Composable
fun PreviewBreedCard() {
    CatalogTheme {
        BreedCard(
            breed = DataSample[0],
            onClick = {},
        )
    }
}