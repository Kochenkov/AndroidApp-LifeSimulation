package com.vkochenkov.lifesimulation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.lifesimulation.model.DataStore

class SettingsViewModel : ViewModel() {

    var dataStore = DataStore
    private var mutableSpeedLiveData = MutableLiveData<Int>()

    val speedLiveData: LiveData<Int> = mutableSpeedLiveData

//    fun onTrackingTouchSpeed(max: Int, position: Int) {
//        val thisPosition  = position + 1
//
//        val currentLongPosition = 1000L/thisPosition
//
//        dataStore.renderingSpeed = currentLongPosition
//
//        mutableSpeedLiveData.postValue(thisPosition)
//    }

    fun onSaveBtnClicked() {
        //todo
    }
}