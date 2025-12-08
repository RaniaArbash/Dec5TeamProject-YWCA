package com.example.dec5teamproject.UILayer


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.dec5teamproject.viewmodel.TouristAttraction
@Composable
fun TouristAttractionDetailScreen(
    attraction: TouristAttraction,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Map category to human-readable description
    val categoryDescription = getCategoryDescription(attraction)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back button
        Button(onClick = { onBack() }) {
            Text("← Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Image
        attraction.imageUrl?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = attraction.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = attraction.name,
            fontSize = 24.sp,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Category description
        Text(
            text = categoryDescription,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Description
        Text(
            text = attraction.description,
            fontSize = 16.sp,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Wikipedia link button
        Button(
            onClick = {
                val wikiUrl =
                    "https://en.wikipedia.org/wiki/${attraction.name.replace(" ", "_")}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiUrl))
                context.startActivity(intent)
            }
        ) {
            Text("Open Wikipedia")
        }
    }
}


/**
 * Maps categories in the attraction to a human-readable description
 */
fun getCategoryDescription(attraction: TouristAttraction): String {
    val categories = attraction.categories  // assuming you added categories property to TouristAttraction
    if (categories.isEmpty()) return "Tourism place"

    return categories.joinToString(", ") { category ->
        when (category) {
            "tourism" -> "Tourist place"
            "tourism.attraction" -> "Tourist attraction"
            "tourism.attraction.artwork" -> "Artwork attraction"
            "tourism.attraction.clock" -> "Clock attraction"
            "tourism.attraction.fountain" -> "Fountain attraction"
            "tourism.attraction.viewpoint" -> "Viewpoint"
            "tourism.information" -> "Tourist information"
            "tourism.sights" -> "Sightseeing spot"
            "tourism.sights.archaeological_site" -> "Archaeological site"
            "tourism.sights.battlefield" -> "Battlefield"
            "tourism.sights.bridge" -> "Bridge"
            "tourism.sights.castle" -> "Castle"
            "religion" -> "Religious site"
            "religion.place_of_worship" -> "Place of worship"
            "camping" -> "Camping site"
            "amenity" -> "Amenity"
            "beach" -> "Beach"
            else -> category
        }
    }
}
