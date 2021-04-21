package com.udacity.asteroidradar.room.dao

import androidx.room.*
import com.udacity.asteroidradar.room.entity.Asteroid

@Dao
interface DaoAsteroid {
    @Query("SELECT * FROM asteroid_tb ORDER BY closeApproachDate DESC")
    suspend fun getAllAsteroid(): List<Asteroid>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllAsteroid(list: List<Asteroid>)

    @Insert
    suspend fun insertAsteroid(asteroid: Asteroid)



}