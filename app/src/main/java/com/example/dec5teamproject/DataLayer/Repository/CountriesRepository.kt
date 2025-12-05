package com.example.dec5teamproject.DataLayer.Repository

import com.example.dec5teamproject.DataLayer.Destinations.RestCountriesApi
import com.example.dec5teamproject.model.Country

// data/repository/CountriesRepository.kt
class CountriesRepository(
    private val api: RestCountriesApi
) {
    suspend fun fetchCountries(): List<Country> {
        return api.getAllCountries()
            .mapNotNull { dto ->
                val name = dto.name?.common ?: return@mapNotNull null
                val flagUrl = dto.flags?.png ?: dto.flags?.svg
                val population = dto.population ?: 0L
                val region = dto.region ?: "Unknown"
                val currencyEntry = dto.currencies?.entries?.firstOrNull()
                val currencyCode = currencyEntry?.key
                val currencyName = currencyEntry?.value?.name

                Country(
                    name = name,
                    flagUrl = flagUrl,
                    population = population,
                    region = region,
                    currency = listOfNotNull(currencyCode, currencyName).joinToString(" - ")
                )
            }
            .sortedBy { it.name }
    }
}


