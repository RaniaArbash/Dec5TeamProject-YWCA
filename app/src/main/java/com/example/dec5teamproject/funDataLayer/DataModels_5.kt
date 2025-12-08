package com.example.dec5teamproject

// Book Models
data class BookResponse(
    val docs: List<Book>
)

data class Book(
    val title: String?,
    val author_name: List<String>?,
    val first_publish_year: Int?,
    val cover_i: String?
) {
    val authorString: String
        get() = author_name?.joinToString(", ") ?: "Unknown Author"

    val coverUrl: String
        get() = if (cover_i != null) {
            "https://covers.openlibrary.org/b/id/$cover_i-M.jpg"
        } else {
            ""
        }
}

// Joke Model
data class Joke(
    val id: Int,
    val type: String,
    val setup: String,
    val punchline: String
)

// Game Models
data class Game(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val short_description: String,
    val genre: String,
    val platform: String,
    val publisher: String
)