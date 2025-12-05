package com.example.dec5teamproject.DataLayer.Destinations
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.http.GET

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
interface RestCountriesApi {
    @GET("v3.1/all?fields=name,flags,population,region,currencies")
    suspend fun getAllCountries(): List<CountryDto>
}


//ApiProvider.kt

object ApiProvider {
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory()) // Required for Kotlin data classes
        .build()

    val restCountriesApi: RestCountriesApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://restcountries.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Pass custom Moshi
            .build()
            .create(RestCountriesApi::class.java)
    }
}
