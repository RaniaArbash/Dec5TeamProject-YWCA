package com.example.dec5teamproject.DataLayer.Models

import com.google.gson.annotations.SerializedName

// ----------------- Geocoding Response -----------------
data class GeoapifyGeocodeResponse(
    val type: String,
    val features: List<GeoapifyGeocodeFeature>
)

data class GeoapifyGeocodeFeature(
    val type: String,
    val geometry: GeoapifyGeometry,
    val properties: GeoapifyGeocodeProperties
)

// Geometry of the feature (lat/lon)
data class GeoapifyGeometry(
    val type: String,
    val coordinates: List<Double> // [lon, lat]
)

// Optional: properties returned by Geoapify geocoding
data class GeoapifyGeocodeProperties(
    val city: String?,
    val country: String?
)

// ----------------- Places Response -----------------
data class GeoapifyPlacesResponse(
    val type: String,
    val features: List<GeoapifyPlaceFeature>
)

data class GeoapifyPlaceFeature(
    val type: String,
    val properties: GeoapifyPlaceProperties
)

// Properties of a place
data class GeoapifyPlaceProperties(
    val name: String?,
    val description: String?,
    val image: String?,
    @SerializedName("categories")
    val categories: List<String>? // API returns array directly
)

// ----------------- Extension properties for convenience -----------------
val GeoapifyGeometry.lat: Double
    get() = coordinates.getOrNull(1) ?: 0.0

val GeoapifyGeometry.lon: Double
    get() = coordinates.getOrNull(0) ?: 0.0