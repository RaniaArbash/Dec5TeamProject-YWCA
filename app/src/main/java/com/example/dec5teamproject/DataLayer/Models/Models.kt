package com.example.dec5teamproject.DataLayer.Models

data class GeoNameResponse(
    val lat: Double,
    val lon: Double
)

// List of features
data class PlacesResponse(
    val features: List<PlaceFeature>
)

// Define PlaceFeature properly
data class PlaceFeature(
    val properties: PlaceProperties
)

// Properties inside each feature
data class PlaceProperties(
    val xid: String,
    val name: String?
)

// Detail of a place
data class PlaceDetailResponse(
    val name: String?,
    val wikipedia_extracts: WikiExtracts?,
    val preview: PreviewImage?
)

data class WikiExtracts(val text: String?)
data class PreviewImage(val source: String?)