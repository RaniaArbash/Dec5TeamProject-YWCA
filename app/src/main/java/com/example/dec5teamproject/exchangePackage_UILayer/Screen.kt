package com.example.exchangeapi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dec5teamproject.ui.theme.Dec5TeamProjectTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun ExchangeRateApp(viewModel: ExchangeViewModel = viewModel()) {
    val data by viewModel.eXState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            if (data != null) {
                FloatingActionButton(
                    onClick = { viewModel.refreshCurrentRates() }
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isLoading && data == null) {
                // Initial loading
                Spacer(modifier = Modifier.height(32.dp))
                CircularProgressIndicator()
                Text(
                    text = "Loading exchange rates...",
                    modifier = Modifier.padding(top = 16.dp)
                )
            } else {
                ExchangeRateContent(
                    response = data,
                    isLoading = isLoading,
                    viewModel = viewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExchangeRateContent(
    response: ExchangeResponse?,
    isLoading: Boolean,
    viewModel: ExchangeViewModel
) {
    // Available currencies - 24 popular ones
    val availableCurrencies = listOf(
        "USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY",
        "INR", "MXN", "BRL", "ZAR", "KRW", "SGD", "HKD", "NZD",
        "SEK", "NOK", "DKK", "PLN", "THB", "IDR", "MYR", "PHP"
    )

    // State for selected base currency
    var selectedBaseCurrency by remember { mutableStateOf("USD") }
    var baseCurrencyExpanded by remember { mutableStateOf(false) }

    // State for target currencies (multi-select)
    var selectedTargetCurrencies by remember {
        mutableStateOf(listOf("EUR", "GBP", "JPY", "AUD", "CAD"))
    }
    var targetCurrencyExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "💱 Currency Exchange",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                if (response != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Updated: ${formatTimestamp(response.timestamp)}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Currency Selection Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Select Currencies",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 12.dp),
                    color = MaterialTheme.colorScheme.primary
                )

                // Base Currency Dropdown
                Text(
                    text = "From:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = baseCurrencyExpanded,
                    onExpandedChange = { baseCurrencyExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedBaseCurrency,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Base Currency") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = baseCurrencyExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = baseCurrencyExpanded,
                        onDismissRequest = { baseCurrencyExpanded = false }
                    ) {
                        availableCurrencies.forEach { currency ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(currency)
                                        if (currency == selectedBaseCurrency) {
                                            Text("✓", color = MaterialTheme.colorScheme.primary)
                                        }
                                    }
                                },
                                onClick = {
                                    selectedBaseCurrency = currency
                                    baseCurrencyExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Target Currencies Dropdown (Multi-Select)
                Text(
                    text = "To: (select multiple)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                ExposedDropdownMenuBox(
                    expanded = targetCurrencyExpanded,
                    onExpandedChange = { targetCurrencyExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedTargetCurrencies.joinToString(", "),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Target Currencies") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = targetCurrencyExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = targetCurrencyExpanded,
                        onDismissRequest = { targetCurrencyExpanded = false }
                    ) {
                        availableCurrencies
                            .filter { it != selectedBaseCurrency }  // Exclude base currency
                            .forEach { currency ->
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(currency)
                                            if (selectedTargetCurrencies.contains(currency)) {
                                                Text(
                                                    text = "✓",
                                                    color = MaterialTheme.colorScheme.primary,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 20.sp
                                                )
                                            }
                                        }
                                    },
                                    onClick = {
                                        selectedTargetCurrencies = if (selectedTargetCurrencies.contains(currency)) {
                                            selectedTargetCurrencies - currency  // Remove
                                        } else {
                                            selectedTargetCurrencies + currency  // Add
                                        }
                                    }
                                )
                            }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Fetch Button
                Button(
                    onClick = {
                        viewModel.fetchCustomRates(
                            baseCurrency = selectedBaseCurrency,
                            targetCurrencies = selectedTargetCurrencies
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading && selectedTargetCurrencies.isNotEmpty()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Loading...")
                    } else {
                        Text("Get Exchange Rates")
                    }
                }

                // Info text
                if (selectedTargetCurrencies.isEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "⚠️ Please select at least one target currency",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }

        // Exchange rates list
        if (response != null && !isLoading) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val formattedRates = response.getFormattedRates()
                    .filter { (currency, _) -> selectedTargetCurrencies.contains(currency) }

                if (formattedRates.isEmpty()) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = "No rates to display. Select target currencies and click 'Get Exchange Rates'",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(formattedRates) { (currency, rate) ->
                        ExchangeRateItem(
                            baseCurrency = response.source,
                            targetCurrency = currency,
                            rate = rate
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExchangeRateItem(
    baseCurrency: String,
    targetCurrency: String,
    rate: Double
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = targetCurrency,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "1 $baseCurrency =",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = String.format("%.4f", rate),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

fun formatTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000)
    val formatter = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
    return formatter.format(date)
}

@Preview(showBackground = true)
@Composable
fun ExchangeRatePreview() {
    Dec5TeamProjectTheme {
        ExchangeRateContent(
            response = ExchangeResponse(
                success = true,
                terms = "https://exchangerate.host/terms",
                privacy = "https://exchangerate.host/privacy",
                timestamp = System.currentTimeMillis() / 1000,
                source = "USD",
                quotes = mapOf(
                    "USDEUR" to 0.92,
                    "USDGBP" to 0.79,
                    "USDJPY" to 149.5,
                    "USDAUD" to 1.52,
                    "USDCAD" to 1.36
                )
            ),
            isLoading = false,
            viewModel = viewModel()
        )
    }
}