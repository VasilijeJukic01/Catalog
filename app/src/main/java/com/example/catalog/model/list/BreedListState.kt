package com.example.catalog.model.list

import com.example.catalog.model.Breed

data class BreedListState (
    val fetching: Boolean = false,
    val breeds: List<Breed> = emptyList(),
    val error: BreedListError? = null
) {
    sealed class BreedListError {
        data class BreedListUpdateFailed(val cause: Throwable? = null) : BreedListError()
    }
}