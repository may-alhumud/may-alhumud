package com.udacity.asteroidradar.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Asteroid_tb")
@Parcelize
data class  Asteroid(
    @PrimaryKey()
    var id: Int,
    var name: String? = null,
    var magnitude: Double? = null,
    var estimatedDiameter: Double? = null,
    var hazardous: Boolean? = null,
    var kilometersPerSecond: Double? = null,
    var distanceFromEarth: Double? = null,
    var closeApproachDate: String? = null
):Parcelable {


}