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
        Log.d("TouristAttractionRepo", "$response" )
        return response.features.map { feature ->
            val desc = when {
                !feature.properties.description.isNullOrEmpty() -> feature.properties.description
                feature.properties.categories.contains("tourism.information") -> "Tourist attraction"
                feature.properties.categories.contains("tourism.sights") -> "Sightseeing spot"
                else -> "Tourism information"
            }

            TouristAttraction(
                name = feature.properties.name ?: "Unknown",
                description = desc,
                imageUrl = feature.properties.image
            )
        }
    }
}
