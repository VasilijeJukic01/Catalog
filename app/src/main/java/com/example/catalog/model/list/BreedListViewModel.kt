package com.example.catalog.model.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.repo.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedListViewModel (
    private val repository: BreedRepository = BreedRepository
) : ViewModel() {

    // Encapsulation
    private val stateFlow = MutableStateFlow(BreedListState())
    val state = stateFlow.asStateFlow()

    private fun setState(reducer: BreedListState.() -> BreedListState) = stateFlow.getAndUpdate(reducer)

    init {
        observeBreeds()
        fetchBreeds()
    }

    private fun observeBreeds() {
        viewModelScope.launch {
            repository.observeBreeds().collect {
                setState { copy(breeds = it) }
            }
        }
    }

    private fun fetchBreeds() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                withContext(Dispatchers.IO) {
                    repository.fetchBreeds()
                }
            } catch (error: Exception) {
                setState { copy(error = BreedListState.BreedListError.BreedListUpdateFailed(cause = error)) }
            } finally {
                setState { copy(fetching = false) }
            }
        }
    }

}