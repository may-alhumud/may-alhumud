package com.udacity.asteroidradar.retrofit


import com.udacity.asteroidradar.retrofit.apiResponse.DailyImageResponse
import okhttp3.ResponseBody

interface ApiHelper {
    suspend fun mainRequest(startDate: String): ResponseBody

    suspend fun getDailyImage(): DailyImageResponse
}