package com.example.dec5teamproject.service


import com.example.dec5teamproject.DataLayer.GeoapifyAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GeoapifyService {
    private const val BASE_URL = "https://api.geoapify.com/"

    val api: GeoapifyAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeoapifyAPI::class.java)
    }
}
