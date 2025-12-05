package com.example.dec5teamproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// API Interfaces
interface OpenLibraryApi {
    @GET("search.json")
    suspend fun searchBooks(
        @Query("q") query: String = "travel",
        @Query("limit") limit: Int = 20
    ): BookResponse
}

interface JokeApi {
    @GET("jokes/random/10")
    suspend fun getRandomJokes(): List<Joke>
}

interface GameApi {
    @GET("games")
    suspend fun getGames(): List<Game>
}

// Retrofit Instances
object ApiClient {
    private val openLibraryRetrofit = Retrofit.Builder()
        .baseUrl("https://openlibrary.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val jokeRetrofit = Retrofit.Builder()
        .baseUrl("https://official-joke-api.appspot.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val gameRetrofit = Retrofit.Builder()
        .baseUrl("https://www.freetogame.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val openLibraryApi: OpenLibraryApi = openLibraryRetrofit.create(OpenLibraryApi::class.java)
    val jokeApi: JokeApi = jokeRetrofit.create(JokeApi::class.java)
    val gameApi: GameApi = gameRetrofit.create(GameApi::class.java)
}