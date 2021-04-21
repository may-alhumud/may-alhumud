package com.udacity.asteroidradar.retrofit.dbUtil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.main.MainViewModel

import com.udacity.asteroidradar.retrofit.ApiHelper
import com.udacity.asteroidradar.room.DatabaseHelper


class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(apiHelper, databaseHelper) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }


}