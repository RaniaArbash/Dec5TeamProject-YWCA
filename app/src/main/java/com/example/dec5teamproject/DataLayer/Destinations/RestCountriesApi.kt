package com.example.dec5teamproject.DataLayer.Destinations
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
interface RestCountriesApi {
    @GET("v3.1/all?fields=name,flags,population,region,currencies")
    suspend fun getAllCountries(): List<CountryDto>
}

object ApiProvider {
    val restCountriesApi: RestCountriesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestCountriesApi::class.java)
    }
}
