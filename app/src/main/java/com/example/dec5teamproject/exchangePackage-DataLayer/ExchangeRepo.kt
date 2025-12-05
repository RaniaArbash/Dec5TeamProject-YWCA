package com.example.exchangeapi


class ExchangeRepo {

    suspend fun getExchangeRates(): ExchangeResponse {
        return ExchangeAPIService.exchange_api.getRates()
    }

    suspend fun getExchangeRatesWithSource(baseCurrency: String): ExchangeResponse {
        return ExchangeAPIService.exchange_api.getRates(
            baseCurrency = baseCurrency
        )
    }

    suspend fun getExchangeRatesWithSpecificCurrencies(
        baseCurrency: String,
        targetCurrencies: List<String>
    ): ExchangeResponse {
        val currenciesString = targetCurrencies.joinToString(",")
        return ExchangeAPIService.exchange_api.getRates(
            baseCurrency = baseCurrency,
            currencies = currenciesString
        )
    }
}

