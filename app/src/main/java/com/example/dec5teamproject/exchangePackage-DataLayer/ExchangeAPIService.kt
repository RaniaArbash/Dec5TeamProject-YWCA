package com.example.exchangeapi

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ExchangeAPIService {

    private val BASE_URL = "https://api.exchangerate.host/"
    val exchange_api : ExchangeAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeAPI::class.java)
    }
}

