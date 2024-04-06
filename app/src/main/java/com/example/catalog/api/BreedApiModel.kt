package com.example.catalog.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreedApiModel(
    val id: String,
    val name: String,
    @SerialName("alt_names")
    val altNames: String,
    val description: String,
    val temperament: String,
    val origin: String,
    val weight: Weight,
    @SerialName("life_span")
    val lifeSpan: String,
    val adaptability: Int,
    @SerialName("affection_level")
    val affectionLevel: Int,
    @SerialName("energy_level")
    val energyLevel: Int,
    val intelligence: Int,
    @SerialName("stranger_friendly")
    val strangerFriendly: Int,
    val rare: Int,
    @SerialName("wikipedia_url")
    val wikipediaUrl: String,
    val image: Image
)

@Serializable
data class Weight(
    val imperial: String,
    val metric: String
)

@Serializable
data class Image(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String
)