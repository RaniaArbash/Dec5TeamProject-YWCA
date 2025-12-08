package com.example.exchangeapi

data class ExchangeResponse(
    val success: Boolean,
    val terms: String,
    val privacy: String,
    val timestamp: Long,
    val source: String,
    val quotes: Map<String, Double>
)

// Extension function to get specific currency rate
fun ExchangeResponse.getRate(targetCurrency: String): Double? {
    val key = source + targetCurrency
    return quotes[key]
}

// Extension function to get all rates as a formatted list
fun ExchangeResponse.getFormattedRates(): List<Pair<String, Double>> {
    return quotes.map { (key, rate) ->
        val targetCurrency = key.removePrefix(source)
        Pair(targetCurrency, rate)
    }
}