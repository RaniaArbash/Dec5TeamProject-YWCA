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
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("HTTP ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}
