package com.example.dec5teamproject.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dec5teamproject.DataLayer.Repo.TouristAttractionRepo
import kotlinx.coroutines.launch

data class TouristAttraction(
    val name: String,
    val description: String,
    val imageUrl: String?,
    val categories: List<String> = emptyList()
)

class TouristAttractionViewModel : ViewModel() {

    private val repo = TouristAttractionRepo()

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
            attractions = emptyList()

            try {
                val (lat, lon) = repo.getCoordinates(city)
                attractions = repo.loadAttractions(lat, lon)

                if (attractions.isEmpty()) {
                    errorMessage = "No attractions found for $city"
                }

            } catch (e: Exception) {
                errorMessage = "Error: ${e.message ?: "Failed to load attractions"}"
            } finally {
                loading = false
            }
        }
    }
}
