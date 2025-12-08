package com.example.dec5teamproject.DataLayer.Repo

import android.util.Log
import com.example.dec5teamproject.DataLayer.Models.*
import com.example.dec5teamproject.service.GeoapifyService
import com.example.dec5teamproject.viewmodel.TouristAttraction

class TouristAttractionRepo {

    private val apiKey = "0d26e9d3f1ea46f99852a351b39ec838"

    // --------------- Get Coordinates for a City ----------------
    suspend fun getCoordinates(city: String): Pair<Double, Double> {
        val response = GeoapifyService.api.geocodeCity(city, apiKey)
        Log.d("TouristAttractionRepo", "Geocode response: $response")
        val geometry = response.features.firstOrNull()?.geometry
            ?: throw Exception("City not found")
        return Pair(geometry.lat, geometry.lon)
    }

    // --------------- Fetch Raw Attractions ----------------
    suspend fun loadRawAttractions(lat: Double, lon: Double): GeoapifyPlacesResponse {
        val filter = "circle:$lon,$lat,5000" // 5 km radius
        return GeoapifyService.api.getPlaces(
            categories = "tourism.sights,tourism.attraction,tourism.information",
            filter = filter,
            limit = 20,
            apiKey = apiKey
        )
    }

    // --------------- Map API Response to TouristAttraction ----------------
    suspend fun loadAttractions(lat: Double, lon: Double): List<TouristAttraction> {
        val response = loadRawAttractions(lat, lon)
        Log.d("TouristAttractionRepo", "Full response: $response")
        Log.d("TouristAttractionRepo", "Features count: ${response.features.size}")

        return response.features.mapIndexed { index, feature ->
            Log.d("TouristAttractionRepo", "Feature $index properties: ${feature.properties}")
            Log.d("TouristAttractionRepo", "Categories: ${feature.properties.categories}")

            val categories = feature.properties.categories ?: emptyList()

            val desc = when {
                !feature.properties.description.isNullOrEmpty() -> feature.properties.description
                categories.contains("tourism.information") -> "Tourist information center"
                categories.contains("tourism.sights") -> "Sightseeing spot"
                categories.contains("tourism.attraction") -> "Tourist attraction"
                else -> "Tourism place"
            }

            TouristAttraction(
                name = feature.properties.name ?: "Unknown",
                description = desc,
                imageUrl = feature.properties.image,
                categories = categories
            )
        }
    }
}