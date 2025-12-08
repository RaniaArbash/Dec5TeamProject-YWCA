package com.example.dec5teamproject

import android.media.Image
import androidx.room.Embedded
import com.squareup.moshi.Json

data class EventsResponse(
    val page: Page?,
    @Json(name = "_embedded") val embedded: EmbeddedEvents?
)

data class Page(
    val size: Int?,
    val totalElements: Int?,
    val totalPages: Int?,
    val number: Int?
)

data class EmbeddedEvents(
    @Json(name = "events") val events: List<Event> = emptyList()
)

data class Event(
    val id: String,
    val name: String,
    val url: String,
    val images: List<EventImage> = emptyList(),
    val dates: Dates,
    @Json(name = "_embedded") val embedded: EventEmbedded? = null
)

data class Dates(
    val start: Start?
)

data class Start(
    val localDate: String?,
    val localTime: String?,
    val dateTime: String?
)

data class EventImage(
    val url: String?
)

data class EventEmbedded(
    @Json(name = "venues") val attractions: List<Venue> = emptyList()
)

data class Venue(
    val name: String?,
    val city: City?,
    val country: Country?
)

data class City(
    val name: String?
)

data class Country(
    val name: String?
)



