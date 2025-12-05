package com.example.dec5teamproject

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TicketmasterService{

    @GET("/discovery/v2/events.json")
    suspend fun getEvents(
        @Query("apikey") apiKey: String,
        @Query("city") city: String? = null,
        @Query("classificationName") classificationName: String? = null,
        @Query("countryCode") countryCode: String? = null,
        @Query("startDateTime") startDateTime: String? = null,
        @Query("endDateTime") endDateTime: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
    ): Response<EventsResponse>
}