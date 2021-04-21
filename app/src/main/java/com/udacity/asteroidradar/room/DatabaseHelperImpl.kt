package com.udacity.asteroidradar.room

import com.udacity.asteroidradar.room.entity.Asteroid
import com.udacity.asteroidradar.room.entity.DailyImage


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getAllAsteroid(): List<Asteroid> {
        return appDatabase.daoAsteroid().getAllAsteroid()
    }

    override suspend fun insertAllAsteroid(list: List<Asteroid>) {
        return appDatabase.daoAsteroid().insertAllAsteroid(list)
    }

    override suspend fun insertAsteroid(asteroid: Asteroid) {
        return appDatabase.daoAsteroid().insertAsteroid(asteroid)
    }

    override suspend fun getDailyImage(): DailyImage? {
        return appDatabase.daoDailyImages().getDailyImage()
    }

    override suspend fun insertDailyImage(dailyImage: DailyImage) {
        return appDatabase.daoDailyImages().insertDailyImage(dailyImage)
    }


}