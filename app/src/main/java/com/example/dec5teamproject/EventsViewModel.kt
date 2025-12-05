package com.example.dec5teamproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class EventsViewModel : ViewModel() {

    private val repository = EventsRepository()

    var events by mutableStateOf<List<Event>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadEvents(city: String?, category: String?, country: String?) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val result = repository.getEvents(city, category, country)
            isLoading = false

            if (result.isSuccess) {
                events = result.getOrNull()?.embedded?.events ?: emptyList()
            } else {
                errorMessage = result.exceptionOrNull()?.message
            }
        }
    }
}
