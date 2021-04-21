package com.udacity.asteroidradar.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dailyImage_tb")
@Parcelize
data class DailyImage(
    val title: String,
    val url: String,
    val mediaType: String
) : Parcelable {

    @PrimaryKey
    var id: Int = 1 // need to single record
}