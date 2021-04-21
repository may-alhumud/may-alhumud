package com.udacity.asteroidradar.retrofit

import com.udacity.asteroidradar.retrofit.apiResponse.DailyImageResponse
import okhttp3.ResponseBody


class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {
    override suspend fun mainRequest(startDate: String): ResponseBody {
        return apiService.mainRequest(startDate)
    }

    override suspend fun getDailyImage(): DailyImageResponse {
        return apiService.getDailyImage()
    }

}