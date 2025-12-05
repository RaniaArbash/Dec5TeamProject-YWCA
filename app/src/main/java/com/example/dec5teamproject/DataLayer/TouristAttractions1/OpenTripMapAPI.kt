package com.example.dec5teamproject.DataLayer.TouristAttractions1



import com.example.dec5teamproject.DataLayer.Models.GeoNameResponse
import com.example.dec5teamproject.DataLayer.Models.PlaceDetailResponse
import com.example.dec5teamproject.DataLayer.Models.PlacesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface OpenTripMapAPI {

    @GET("0.1/en/places/geoname")
    suspend fun getGeoName(
        @Query("name") city: String,
        @Query("apikey") apiKey: String
    ): GeoNameResponse

    @GET("0.1/en/places/radius")
    suspend fun getPlacesInRadius(
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("radius") radius: Int = 5000,
        @Query("limit") limit: Int = 20,
        @Query("apikey") apiKey: String
    ): PlacesResponse

    @GET("0.1/en/places/xid/{xid}")
    suspend fun getPlaceDetails(
        @Path("xid") xid: String,
        @Query("apikey") apiKey: String
    ): PlaceDetailResponse
}
