package com.example.dec5teamproject.UILayer
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.dec5teamproject.model.Country
import com.example.dec5teamproject.viewmodel.CountriesUiState
import com.example.dec5teamproject.viewmodel.CountriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopularDestinationsScreen(
    viewModel: CountriesViewModel = viewModel()
) {
    val uiState by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Popular Destinations") })
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (val state = uiState) {
                is CountriesUiState.Loading -> LoadingState()
                is CountriesUiState.Error -> ErrorState(
                    message = state.message,
                    onRetry = { viewModel.refresh() }
                )
                is CountriesUiState.Success -> CountriesGrid(
                    countries = state.countries
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(message, style = MaterialTheme.typography.bodyLarge)
        Spacer(Modifier.height(12.dp))
        Button(onClick = onRetry) { Text("Retry") }
    }
}

@Composable
private fun CountriesGrid(
    countries: List<Country>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(countries, key = { it.name }) { country ->
            CountryCard(country = country)
        }
    }
}

@Composable
private fun CountryCard(
    country: Country
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(12.dp)) {
            AsyncImage(
                model = country.flagUrl,
                contentDescription = "${country.name} flag",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(country.name, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text("Region: ${country.region}", style = MaterialTheme.typography.bodyMedium)
            Text("Population: ${formatPopulation(country.population)}", style = MaterialTheme.typography.bodyMedium)
            Text("Currency: ${country.currency}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

private fun formatPopulation(population: Long): String {
    return when {
        population >= 1_000_000_000 -> String.format("%.1fB", population / 1_000_000_000.0)
        population >= 1_000_000 -> String.format("%.1fM", population / 1_000_000.0)
        population >= 1_000 -> String.format("%.1fk", population / 1_000.0)
        else -> population.toString()
    }
}