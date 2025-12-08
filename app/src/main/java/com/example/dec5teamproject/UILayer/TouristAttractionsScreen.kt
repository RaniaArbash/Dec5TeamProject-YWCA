package com.example.dec5teamproject.UILayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.dec5teamproject.viewmodel.TouristAttraction
import com.example.dec5teamproject.viewmodel.TouristAttractionViewModel
import kotlinx.coroutines.launch

@Composable
fun TouristAttractionsScreen(viewModel: TouristAttractionViewModel = viewModel()) {
    val attractions = viewModel.attractions
    val loading = viewModel.loading
    val error = viewModel.errorMessage
    var city by remember { mutableStateOf("") }
    var selectedAttraction by remember { mutableStateOf<TouristAttraction?>(null) }
    val scope = rememberCoroutineScope()

    if (selectedAttraction != null) {
        // Show detail screen
        TouristAttractionDetailScreen(
            attraction = selectedAttraction!!,
            onBack = { selectedAttraction = null } // back to list
        )
    } else {
        // Show list screen
        Column(modifier = Modifier.padding(16.dp)) {

            // Screen heading
            Text(
                text = "Tourist Attractions",
                fontSize = 28.sp,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("Enter city") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { scope.launch { viewModel.search(city) } },
                modifier = Modifier.padding(top = 12.dp, bottom = 16.dp)
            ) {
                Text("Search")
            }

            if (loading) CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

            ListTouristAttractions(attractions) { attraction ->
                selectedAttraction = attraction
            }
        }
    }
}

@Composable
fun ListTouristAttractions(
    attractions: List<TouristAttraction>,
    onAttractionClick: (TouristAttraction) -> Unit
) {
    LazyColumn {
        items(attractions) { attraction ->
            AttractionCard(attraction, onAttractionClick)
        }
    }
}

@Composable
fun AttractionCard(
    attraction: TouristAttraction,
    onAttractionClick: (TouristAttraction) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onAttractionClick(attraction) }
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            attraction.imageUrl?.let {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(context)
                            .data(it)
                            .crossfade(true)
                            .build()
                    ),
                    contentDescription = attraction.name,
                    modifier = Modifier.size(90.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(attraction.name, fontSize = 18.sp)
                Text(attraction.description, maxLines = 3, fontSize = 14.sp)
            }
        }
    }
}
