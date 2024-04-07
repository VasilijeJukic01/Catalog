package com.example.catalog.model.details

import com.example.catalog.model.Breed

data class BreedDetailsState (
    val fetching: Boolean = false,
    val breedId: String = "",
    val data: Breed? = null,
    val navigateToWiki: String? = null,
    val error: BreedDetailsError? = null
) {
    sealed class BreedDetailsError {
        data class BreedDetailsUpdateFailed(val cause: Throwable? = null) : BreedDetailsError()
    }
}