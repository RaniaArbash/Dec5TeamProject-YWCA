package com.example.exchangeapi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ExchangeViewModel : ViewModel() {

    private val exchangeRepo = ExchangeRepo()

    private val _eXState = MutableStateFlow<ExchangeResponse?>(null)
    val eXState: StateFlow<ExchangeResponse?> = _eXState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Store current selection for refresh
    private var currentBaseCurrency: String = "USD"
    private var currentTargetCurrencies: List<String> = listOf("EUR", "GBP", "JPY", "AUD", "CAD")

    // Fetch rates with default parameters
    fun fetchRates() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = exchangeRepo.getExchangeRates()
            _eXState.value = result
            _isLoading.value = false
        }
    }

    // Fetch rates with custom base currency only
    fun fetchRatesWithCustomSource(baseCurrency: String) {
        currentBaseCurrency = baseCurrency
        viewModelScope.launch {
            _isLoading.value = true
            val result = exchangeRepo.getExchangeRatesWithSource(baseCurrency)
            _eXState.value = result
            _isLoading.value = false
        }
    }

    // Fetch rates with custom base currency AND specific target currencies
    fun fetchCustomRates(baseCurrency: String, targetCurrencies: List<String>) {
        if (targetCurrencies.isEmpty()) {
            return
        }

        // Store current selection for refresh
        currentBaseCurrency = baseCurrency
        currentTargetCurrencies = targetCurrencies

        viewModelScope.launch {
            _isLoading.value = true
            val result = exchangeRepo.getExchangeRatesWithSpecificCurrencies(
                baseCurrency = baseCurrency,
                targetCurrencies = targetCurrencies
            )
            _eXState.value = result
            _isLoading.value = false
        }
    }

    // Refresh with current selection
    fun refreshCurrentRates() {
        fetchCustomRates(currentBaseCurrency, currentTargetCurrencies)
    }
}