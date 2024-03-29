package com.example.catalog.model

data class Breed(
    val id: String,
    val name: String,
    val altNames: List<String>,
    val description: String,
    val temperament: List<String>,
) {

}