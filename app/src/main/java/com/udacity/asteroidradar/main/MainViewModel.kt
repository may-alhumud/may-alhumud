package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.retrofit.ApiHelper
import com.udacity.asteroidradar.retrofit.apiResponse.DailyImageResponse
import com.udacity.asteroidradar.retrofit.dbUtil.Resource
import com.udacity.asteroidradar.room.DatabaseHelper
import com.udacity.asteroidradar.room.entity.Asteroid
import com.udacity.asteroidradar.room.entity.DailyImage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) : ViewModel() {
    val TAG = "MainViewModel"
    private val list = MutableLiveData<Resource<List<Asteroid>>>()
    private val dailyImage = MutableLiveData<Resource<DailyImage>>()
    val listAsteroid = ArrayList<Asteroid>()


    fun fetchMainResponse() {
        viewModelScope.launch {
            list.postValue(Resource.loading(null))

            try {
                val db: List<Asteroid> = databaseHelper.getAllAsteroid()
                if (db.isEmpty()) {
                    fetchRemoteData(getCurrentDate())
                }


                list.postValue(Resource.success(databaseHelper.getAllAsteroid()))

                fetchRemoteData(getCurrentDate())

            } catch (ex: Exception) {
                Log.e(TAG, "fetchMainResponseEx: ${ex.message}")
                list.postValue(Resource.error(ex.message.toString(), null))
            }
        }
    }

    fun fetchDailyImage() {

        viewModelScope.launch {
            dailyImage.postValue(Resource.loading(null))
            try {
                val db = databaseHelper.getDailyImage()


                val request: DailyImageResponse
                if (db == null) {
                    request = apiHelper.getDailyImage()
                    if (request.media_type == "image") {
                        databaseHelper.insertDailyImage(
                            DailyImage(
                                request.title,
                                request.url,
                                request.media_type
                            )
                        )
                        dailyImage.postValue(Resource.success(databaseHelper.getDailyImage()))
                    } else {
                        dailyImage.postValue(Resource.empty())
                    }
                } else {
                    dailyImage.postValue(Resource.success(db))
                }

            } catch (ex: Exception) {
                Log.e(TAG, "fetchDailyImageEx: ${ex.message}")
                dailyImage.postValue(Resource.error(ex.message.toString(), null))
            }
        }
    }


    fun getMainList(): LiveData<Resource<List<Asteroid>>> {
        return list
    }

    fun getDailyImage(): LiveData<Resource<DailyImage>> {
        return dailyImage
    }

    private suspend fun fetchRemoteData(startDate: String) {
        var json = Gson().toJson(apiHelper.mainRequest(startDate).string())
        json = json.replace("\\\"", "'")
        val jsonObj = JSONObject(json.substring(1, json.length - 1))

        for (obj in parseAsteroidsJsonResult(jsonObj)) {
            val asteroid = Asteroid(
                obj.id.toInt(),
                obj.codename,
                obj.absoluteMagnitude,
                obj.estimatedDiameter,
                obj.isPotentiallyHazardous,
                obj.relativeVelocity,
                obj.distanceFromEarth,
                obj.closeApproachDate
            )
            listAsteroid.add(asteroid)
            databaseHelper.insertAllAsteroid(listAsteroid)
        }
    }

    private fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val currentTime = calendar.time
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(currentTime)
    }
}