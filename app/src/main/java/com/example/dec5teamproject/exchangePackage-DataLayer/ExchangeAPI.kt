package com.example.exchangeapi

import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeAPI{

    @GET("live")
    suspend fun getRates(@Query("access_key") apikey: String = "92c7a2f591b4b84b5131b8ca6e316707",
                                @Query(value = "source") baseCurrency: String = "CAD",
                                @Query(value = "currencies") currencies: String = "USD,EUR,JPY,AUD,GBP") : ExchangeResponse

}


