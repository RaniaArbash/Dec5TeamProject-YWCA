package com.example.dec5teamproject

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventsScreen(viewModel: EventsViewModel) {

    val context = LocalContext.current

// Filter state
    var city by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    val events = viewModel.events
    val isLoading = viewModel.isLoading
    val error = viewModel.errorMessage

    val categories = listOf("Music", "Sports", "Arts & Theatre", "Film", "Miscellaneous")
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadEvents(city, category, "US")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // Filters
        Column(modifier = Modifier.fillMaxWidth()) {

            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = { Text("City") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    label = { Text("Category") },
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { c ->
                        DropdownMenuItem(
                            text = { Text(c) },
                            onClick = {
                                category = c
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = { viewModel.loadEvents(
                    city = if (city.isBlank()) null else city,
                    category = if (category.isBlank()) null else category,
                    country = ""
                ) },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
            ) {
                Text("Filter")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    }
                }
                events.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No events found.")
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(events) { event ->
                            EventCard(event, context)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(event: Event, context: android.content.Context) {

    val imageUrl = event.images.firstOrNull()?.url
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
                context.startActivity(intent)
            }
            .padding(8.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Image(
                painter = rememberAsyncImagePainter(imageUrl ?: "https://via.placeholder.com/300x180.png?text=No+Image"),
                contentDescription = event.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = androidx.compose.ui.layout.ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(event.name, style = MaterialTheme.typography.titleLarge)

            event.dates.start?.localDate?.let {
                Text("Date: $it")
            }

            event.embedded?.attractions?.firstOrNull()?.city?.name?.let {
                Text("City: $it")
            }
        }
    }
}
