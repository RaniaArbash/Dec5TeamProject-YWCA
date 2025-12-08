package com.example.dec5teamproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dec5teamproject.DataLayer.Destinations.ApiProvider
import com.example.dec5teamproject.DataLayer.Repository.CountriesRepository
import com.example.dec5teamproject.model.Country
import kotlinx.coroutines.launch

sealed interface CountriesUiState {
    data object Loading : CountriesUiState
    data class Success(val countries: List<Country>) : CountriesUiState
    data class Error(val message: String) : CountriesUiState
}

class CountriesViewModel(
    private val repository: CountriesRepository = CountriesRepository(ApiProvider.restCountriesApi)
) : ViewModel() {

    var uiState: CountriesUiState = CountriesUiState.Loading
        private set

    // expose as StateFlow for Compose
    private val _state = kotlinx.coroutines.flow.MutableStateFlow<CountriesUiState>(CountriesUiState.Loading)
    val state = _state

    init { refresh() }

    fun refresh() {
        _state.value = CountriesUiState.Loading
        viewModelScope.launch {
            try {
                val list = repository.fetchCountries()
                _state.value = if (list.isEmpty()) {
                    CountriesUiState.Error("No countries available.")
                } else {
                    CountriesUiState.Success(list)
                }
            } catch (e: Exception) {
                _state.value = CountriesUiState.Error("Failed to load: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }
}

interface CountriesRepository {

}
