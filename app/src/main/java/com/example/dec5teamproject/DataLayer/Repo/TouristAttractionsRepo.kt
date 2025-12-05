package com.example.dec5teamproject.DataLayer.Repo

import com.example.dec5teamproject.DataLayer.TouristAttractions1.OpenTripMapService
import com.example.dec5teamproject.viewmodel.TouristAttraction

class TouristAttractionsRepo {


    private val apiKey = "5ae2e3f221c38a28845f05b6773da1afdbb8ccbbf1b7c3079937dd7d"
    private val api = OpenTripMapService.api

    suspend fun loadAttractions(city: String): List<TouristAttraction> {
        try {
            // Get coordinates for the city
            val geo = api.getGeoName(city, apiKey)

            // Get places within 5km radius
            val places = api.getPlacesInRadius(
                lon = geo.lon,
                lat = geo.lat,
                radius = 5000,
                limit = 20,
                apiKey = apiKey
            )


            val result = mutableListOf<TouristAttraction>()

            for (item in places.features) {
                try {
                    val detail = api.getPlaceDetails(item.properties.xid, apiKey)

                    val name = detail.name ?: item.properties.name ?: "Unknown"
                    val description = detail.wikipedia_extracts?.text ?: "No description available"

                    result.add(
                        TouristAttraction(
                            name = name,
                            description = description,
                            imageUrl = detail.preview?.source
                        )
                    )
                } catch (e: Exception) {

                    continue
                }
            }

            return result

        } catch (e: Exception) {
            throw Exception("Failed to load attractions: ${e.message}", e)
        }
    }
}