package com.example.dec5teamproject.DataLayer


import com.example.dec5teamproject.DataLayer.Models.GeoapifyGeocodeResponse
import com.example.dec5teamproject.DataLayer.Models.GeoapifyPlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoapifyAPI {

    // Fetch places / tourist attractions
    @GET("v2/places")
    suspend fun getPlaces(
        @Query("categories") categories: String = "tourism.attraction,tourism.sights,tourism.information",
        @Query("filter") filter: String,
        @Query("limit") limit: Int = 20,
        @Query("apiKey") apiKey: String
    ): GeoapifyPlacesResponse

    // Geocoding: city -> coordinates
    @GET("v1/geocode/search")
    suspend fun geocodeCity(
        @Query("text") city: String,
        @Query("apiKey") apiKey: String
    ): GeoapifyGeocodeResponse
}
