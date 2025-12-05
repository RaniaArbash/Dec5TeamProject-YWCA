package com.example.dec5teamproject.DataLayer.TouristAttractions1



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenTripMapService {

    private const val BASE_URL = "https://api.opentripmap.com/"

    val api: OpenTripMapAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenTripMapAPI::class.java)
    }
}
