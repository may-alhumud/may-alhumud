package com.udacity.asteroidradar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.room.dao.DaoAsteroid
import com.udacity.asteroidradar.room.dao.DaoDailyImages
import com.udacity.asteroidradar.room.entity.Asteroid
import com.udacity.asteroidradar.room.entity.DailyImage

@Database(entities = [Asteroid::class, DailyImage::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun daoAsteroid(): DaoAsteroid
    abstract fun daoDailyImages(): DaoDailyImages


    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = buildRoomDB(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "db-asteroid"
            )
                .fallbackToDestructiveMigration()
               // .allowMainThreadQueries()
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // add this code
                        db.execSQL("PRAGMA encoding='UTF-8';")
                    }
                }).build()
        }
    }

}