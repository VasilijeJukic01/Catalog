package com.example.catalog.model.details

sealed class BreedDetailsUiEvent {
    data class VisitWiki(val link: String) : BreedDetailsUiEvent()
}