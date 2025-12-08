package com.example.dec5teamproject.UILayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.dec5teamproject.viewmodel.TouristAttraction
import com.example.dec5teamproject.viewmodel.TouristAttractionsViewModel
import kotlinx.coroutines.launch

@Composable
fun TouristAttractionsScreen(viewModel: TouristAttractionsViewModel = viewModel()) {

    val attractions = viewModel.attractions
    val loading = viewModel.loading
    var city by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Enter city") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { scope.launch { viewModel.search(city) } },
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Search")
        }

        if (loading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        ListTouristAttractions(attractions)
    }
}
@Composable
fun ListTouristAttractions(attractions: List<TouristAttraction>) {
    LazyColumn {
        items(attractions) { attraction ->
            AttractionCard(attraction)
        }
    }
}

@Composable
fun AttractionCard(attraction: TouristAttraction) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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

