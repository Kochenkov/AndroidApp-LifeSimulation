package com.vkochenkov.lifesimulation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.model.DataStore
import com.vkochenkov.lifesimulation.presentation.activity.SettingsActivity

class SettingsViewModel : ViewModel() {

    var dataStore = DataStore
    private var mutableSpeedLiveData = MutableLiveData<Double>()

    val speedLiveData: LiveData<Double> = mutableSpeedLiveData

    fun onStart() {
        mutableSpeedLiveData.postValue(1000.0/dataStore.renderingSpeed)
    }

    fun onStop() {}

    fun onSaveBtnClicked(activity: SettingsActivity) {
        dataStore.renderingSpeed = 1000.0/activity.speedEdt.text.toString().toDouble()
        mutableSpeedLiveData.postValue(1000.0/dataStore.renderingSpeed)
        dataStore.cellsField = CellsField(dataStore.cellsAmountPerWidth, dataStore.randomAliveFactor)
        activity.onBackPressed()
    }
}