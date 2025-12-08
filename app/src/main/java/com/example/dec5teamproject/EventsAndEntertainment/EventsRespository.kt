package com.example.dec5teamproject

import com.example.dec5teamproject.BuildConfig

class EventsRepository {

    suspend fun getEvents(
        city: String?,
        category: String?,
        country: String?,
        page: Int = 0
    ): Result<EventsResponse>{
        return try {
            println("Using API Key: ${BuildConfig.TM_API_KEY}")
            val response = RetrofitInstance.api.getEvents(
                apiKey = BuildConfig.TM_API_KEY,
            city = city,
            classificationName = category,
            countryCode = country,
            startDateTime = null,
            endDateTime = null,
            page = page,
            size = 20
            )

            if (response.isSuccessful && response.body() != null){
                val body = response.body()!!
                val safeBody = body.copy(
                    embedded = body.embedded ?: EmbeddedEvents(emptyList())
                )
                Result.success(safeBody)
            } else {
                Result.failure(Exception("HTTP ${response.code()} - ${response.message()}"))
            }

        } catch (e: Exception){
            Result.failure(e)
        }
    }
}
