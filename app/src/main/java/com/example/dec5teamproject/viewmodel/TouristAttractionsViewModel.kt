package com.example.dec5teamproject.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dec5teamproject.DataLayer.Repo.TouristAttractionsRepo
import kotlinx.coroutines.launch

data class TouristAttraction(
    val name: String,
    val description: String,
    val imageUrl: String?
)

class TouristAttractionsViewModel : ViewModel() {

    private val repo = TouristAttractionsRepo()

    var attractions by mutableStateOf<List<TouristAttraction>>(emptyList())
        private set

    var loading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun search(city: String) {
        if (city.isBlank()) {
            errorMessage = "Please enter a city name"
            return
        }

        viewModelScope.launch {
            loading = true
            errorMessage = null

            try {
                attractions = repo.loadAttractions(city)

                if (attractions.isEmpty()) {
                    errorMessage = "No attractions found for $city"
                }
            } catch (e: Exception) {
                val msg = "Error: ${e.message ?: "Failed to load attractions"}"
                errorMessage = msg
                attractions = emptyList()
            } finally {
                loading = false
            }
        }
    }
}