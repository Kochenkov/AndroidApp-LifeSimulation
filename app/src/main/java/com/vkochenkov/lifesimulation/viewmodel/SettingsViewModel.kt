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
    private var mutableSizeLiveData = MutableLiveData<Int>()

    val speedLiveData: LiveData<Double> = mutableSpeedLiveData
    var sizeLiveData: LiveData<Int> = mutableSizeLiveData

    fun onStart() {
        mutableSpeedLiveData.postValue(1000.0/dataStore.renderingSpeed)
        mutableSizeLiveData.postValue(dataStore.sizeCellsPerWidth)
    }

    fun onStop() {}

    fun onSaveBtnClicked(activity: SettingsActivity) {
        dataStore.renderingSpeed = 1000.0/activity.speedEdt.text.toString().toDouble()
        dataStore.sizeCellsPerWidth = activity.sizeEdt.text.toString().toInt()

        mutableSpeedLiveData.postValue(1000.0/dataStore.renderingSpeed)
        mutableSizeLiveData.postValue(dataStore.sizeCellsPerWidth)

        dataStore.cellsField = CellsField(dataStore.sizeCellsPerWidth, dataStore.randomAliveFactor)

        activity.onBackPressed()
    }
}