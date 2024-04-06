package com.example.catalog.model.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.repo.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedDetailsViewModel (
    private val breedId: String,
    private val repository: BreedRepository = BreedRepository
) : ViewModel() {

    // Encapsulation
    private val stateFlow = MutableStateFlow(BreedDetailsState(breedId = breedId))
    val state = stateFlow.asStateFlow()

    private fun setState(reducer: BreedDetailsState.() -> BreedDetailsState) = stateFlow.getAndUpdate(reducer)

    init {
        observeBreed()
        fetchBreed()
    }

    private fun observeBreed() {
        viewModelScope.launch {
            repository.observeBreedDetails(breedId)
                .filterNotNull()
                .collect { setState { copy(data = it) } }
        }
    }

    private fun fetchBreed() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                withContext(Dispatchers.IO) {
                    repository.fetchBreedDetails(breedId)
                }
            } catch (e: Exception) {
                setState {
                    copy(error = BreedDetailsState.BreedDetailsError.BreedDetailsUpdateFailed(e))
                }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }


}