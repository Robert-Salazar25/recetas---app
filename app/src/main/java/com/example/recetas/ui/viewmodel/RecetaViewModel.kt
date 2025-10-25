package com.example.recetas.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recetas.domain.model.Receta
import com.example.recetas.domain.repository.RecetaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecetaViewModel @Inject constructor(
    private val repository: RecetaRepository
) : ViewModel() {

    private val _recetas = MutableStateFlow<List<Receta>>(emptyList())
    val recetas: StateFlow<List<Receta>> = _recetas.asStateFlow()

    private val _searchResults = MutableStateFlow<List<Receta>>(emptyList())
    val searchResults: StateFlow<List<Receta>> = _searchResults.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isSearchActive = MutableStateFlow(false)
    val isSearchActive: StateFlow<Boolean> = _isSearchActive

    private var searchJob: Job? = null

    // Función para actualizar query con debounce
    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(300) // Debounce de 500ms
            if (query.length >= 2) {
                _searchResults.value = repository.searchRecetas(query)
            } else {
                _searchResults.value = emptyList()
            }
        }
    }

    fun toggleSearch(active: Boolean){
        _isSearchActive.value = active
        if (!active){
            _searchQuery.value = ""
            _searchResults.value = emptyList()
        }
    }

    fun searchRecetas(){
        viewModelScope.launch {
            _searchResults.value = repository.searchRecetas(_searchQuery.value)
        }
    }

    init {
        loadRecipes()
    }

    private fun loadRecipes() {
        viewModelScope.launch {
            repository.getRecetas().collect { _recetas.value = it }
        }
    }
}

