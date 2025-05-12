package com.reddit.rickandmortyapp.network

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RickAndMortyViewModel(
    private val repository: RickAndMortyRepository
) : ViewModel() {
    private val _items = mutableStateOf<List<RickAndMortyCharacter>>(emptyList())
    val items: State<List<RickAndMortyCharacter>> = _items

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private var currentPage = 1
    var isLoading = mutableStateOf(false)
    var hasMoreData = mutableStateOf(true)

    init {
        fetchCharacters()
    }

    private fun fetchCharacters() {
        if (isLoading.value) return
        isLoading.value = true
        viewModelScope.launch {
            val maxRetries = 2

            repeat(maxRetries) { attempt ->
                try {
                    val charResult = repository.getCharactersResult(currentPage)
                    _items.value = _items.value + charResult.results
                    hasMoreData.value = charResult.info.next != null
                    isLoading.value = false
                    return@launch
                } catch (e: Exception) {
                    if (attempt == maxRetries - 1) {
                        _error.value = e.message
                        isLoading.value = false
                    } else {
                        delay(1000)
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        if (hasMoreData.value && !isLoading.value) {
            currentPage++
            fetchCharacters()
        }
    }
}