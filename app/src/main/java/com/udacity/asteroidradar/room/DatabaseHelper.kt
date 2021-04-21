package com.udacity.asteroidradar.room

import com.udacity.asteroidradar.room.entity.Asteroid
import com.udacity.asteroidradar.room.entity.DailyImage

interface DatabaseHelper {
    suspend fun getAllAsteroid(): List<Asteroid>

    suspend fun insertAllAsteroid(list: List<Asteroid>)

    suspend fun insertAsteroid(asteroid: Asteroid)

    suspend fun getDailyImage(): DailyImage?

    suspend fun insertDailyImage(dailyImage: DailyImage)


}