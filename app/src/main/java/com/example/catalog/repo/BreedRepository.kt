package com.example.catalog.repo

import com.example.catalog.api.BreedsApi
import com.example.catalog.api.networking.retrofit
import com.example.catalog.api.toBreed
import com.example.catalog.model.Breed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

object BreedRepository {

    private val breedsApi: BreedsApi = retrofit.create(BreedsApi::class.java)
    private val breedList = MutableStateFlow(listOf<Breed>())

    // Flow
    suspend fun fetchAllBreeds() {
        val breedApiModels = breedsApi.getAllBreeds()
        val breeds = breedApiModels.map { it.toBreed() }
        breedList.update { breeds }
    }

    fun fetchBreedDetails(breedId: String): Breed? {
        return breedList.value.find { it.id == breedId }
    }

    // CRUD
    fun allBreeds() : List<Breed> = breedList.value

    fun getById(id: String) : Breed? {
        return breedList.value.find {
            it.id == id
        }
    }

}