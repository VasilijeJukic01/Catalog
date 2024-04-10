package com.example.catalog.model.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catalog.repo.BreedRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreedListViewModel (
    private val repository: BreedRepository
) : ViewModel() {

    // State
    private val stateFlow = MutableStateFlow(BreedListState())
    val state = stateFlow.asStateFlow()

    private fun setState(reducer: BreedListState.() -> BreedListState) = stateFlow.getAndUpdate(reducer)

    // Event
    private val events = MutableSharedFlow<BreedListUiEvent>()

    fun setEvent(event: BreedListUiEvent) = viewModelScope.launch { events.emit(event) }

    init {
        handleEvents()
        fetchBreeds()
    }

    // Events
    @OptIn(FlowPreview::class)
    private fun handleEvents() {
        viewModelScope.launch {
            events
                .debounce(300)
                .collect { event ->
                    handleEvent(event)
                }
        }
    }

    private fun handleEvent(event: BreedListUiEvent) {
        when (event) {
            // Search
            is BreedListUiEvent.SearchChanged -> {
                setState { copy(filter = event.text) }
                handleSearch()
                println("Search changed: ${event.text}")
            }
        }
    }

    // Event handlers
    private fun handleSearch() {
        val filter = stateFlow.value.filter
        val breeds = stateFlow.value.breeds
        val filtered = breeds.filter { it.name.contains(filter, ignoreCase = true) }
        setState { copy(currentBreeds = filtered) }
    }

    // Fetching
    private fun fetchBreeds() {
        viewModelScope.launch {
            setState { copy(fetching = true) }
            try {
                withContext(Dispatchers.IO) {
                    repository.fetchAllBreeds()
                    println("Fetched breeds")
                }
                setState { copy(breeds = repository.allBreeds(), currentBreeds = repository.allBreeds()) }
            } catch (error: Exception) {
                setState { copy(error = BreedListState.BreedListError.BreedListUpdateFailed(cause = error)) }
            } finally {
                setState { copy(fetching = false) }
                println("Fetch breeds done")
            }
        }
    }

}