package com.example.catalog.api

import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsApi {

    @GET("breeds")
    suspend fun getAllBreeds(): List<BreedApiModel>

    @GET("breeds/{id}")
    suspend fun getBreed(
        @Path("id") breedId: Int,
    ): BreedApiModel

    @GET("images/{id}")
    suspend fun getImage(
        @Path("id") imageId: Int,
    ): List<BreedApiModel>

}