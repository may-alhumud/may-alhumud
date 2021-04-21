package com.udacity.asteroidradar.retrofit

import com.udacity.asteroidradar.retrofit.apiResponse.DailyImageResponse
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {

    @GET("neo/rest/v1/feed")
    suspend fun mainRequest(
        @Query("start_date") startDate: String,
        @Query("api_key") key: String = "EWMHtoskp7E0HC3scYRQGWJJeynBfDIvfbkMctJx"
    ): ResponseBody

    @GET("planetary/apod")
    suspend fun getDailyImage(
        @Query("api_key") key: String = "EWMHtoskp7E0HC3scYRQGWJJeynBfDIvfbkMctJx"
    ): DailyImageResponse


}