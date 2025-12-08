package com.example.dec5teamproject.DataLayer.Destinations
data class CountryDto(
    val name: NameDto?,
    val flags: FlagsDto?,
    val population: Long?,
    val region: String?,
    val currencies: Map<String, CurrencyDto>?
)

data class NameDto(val common: String?)
data class FlagsDto(val png: String?, val svg: String?)
data class CurrencyDto(val name: String?, val symbol: String?)
