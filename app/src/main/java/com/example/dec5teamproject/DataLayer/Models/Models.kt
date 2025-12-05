package com.example.dec5teamproject.DataLayer.Models

import com.google.android.gms.maps.model.PlaceFeature

data class GeoNameResponse(
    val lat: Double,
    val lon: Double
)
data class PlacesResponse(
    val features: List<PlaceFeature>
)
data class PlaceFeature(
    val properties: PlaceProperties
)

data class PlaceProperties(
    val xid: String,
    val name: String?
)

data class PlaceDetailResponse(
    val name: String?,
    val wikipedia_extracts: WikiExtracts?,
    val preview: PreviewImage?
)

data class WikiExtracts(val text: String?)
data class PreviewImage(val source: String?)
