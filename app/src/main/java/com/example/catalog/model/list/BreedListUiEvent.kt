package com.example.catalog.model.list

sealed class BreedListUiEvent {
    data class SearchChanged(val text: String) : BreedListUiEvent()
}