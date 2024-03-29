package com.example.catalog.repo

import com.example.catalog.model.Breed

object DataRepo {

    private var breedList = DataSample.toMutableList()

    fun allBreeds() : List<Breed> = breedList

    fun getById(id: String) : Breed? {
        return breedList.find {
            it.id == id
        }
    }

    fun remove(id: String) {
        val idx = breedList.indexOf(
            breedList.find {
                it.id == id
            }
        )
        if (idx != -1) {
            breedList.removeAt(idx)
        }
    }

}