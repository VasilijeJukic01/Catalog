package com.example.catalog.repo

import com.example.catalog.model.Breed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlin.time.Duration.Companion.seconds

object BreedRepository {

    private val breedList = MutableStateFlow(listOf<Breed>())

    // Flow
    // TODO: Change later to API call
    suspend fun fetchBreeds() {
        delay(2.seconds)
        breedList.update { DataSample.toMutableList() }
    }

    suspend fun fetchBreedDetails(breedId: String) {
        delay(1.seconds)
    }

    private fun observeBreeds() : Flow<List<Breed>> = breedList.asStateFlow()

    fun observeBreedDetails(breedId: String) : Flow<Breed?> {
        return observeBreeds()
            .map { breeds -> breeds.find { it.id == breedId } }
    }

    // CRUD
    fun allBreeds() : List<Breed> = breedList.value

    fun getById(id: String) : Breed? {
        return breedList.value.find {
            it.id == id
        }
    }

}