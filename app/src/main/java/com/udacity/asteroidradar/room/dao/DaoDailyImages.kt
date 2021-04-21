package com.udacity.asteroidradar.room.dao

import androidx.room.*
import com.udacity.asteroidradar.room.entity.Asteroid
import com.udacity.asteroidradar.room.entity.DailyImage

@Dao
interface DaoDailyImages {
    @Query("SELECT * FROM dailyImage_tb")
    suspend fun getDailyImage(): DailyImage?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyImage(dailyImage: DailyImage)



}