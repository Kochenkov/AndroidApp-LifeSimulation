package com.vkochenkov.lifesimulation.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.model.DataStore

class SettingsViewModel : ViewModel() {

    var dataStore = DataStore
    private var speedMutableLiveData = MutableLiveData<Double>()
    private var sizeMutableLiveData = MutableLiveData<Int>()

    val speedLiveData: LiveData<Double> = speedMutableLiveData
    var sizeLiveData: LiveData<Int> = sizeMutableLiveData

    fun onStart() {
        speedMutableLiveData.postValue(1000.0 / dataStore.renderingSpeed)
        sizeMutableLiveData.postValue(dataStore.sizeCellsPerWidth)
    }

    fun onStop() {}

    fun onSaveBtnClicked(speed: String, size: String) {
        dataStore.renderingSpeed = 1000.0 / speed.toDouble()
        dataStore.speedToShow = 1000.0 / dataStore.renderingSpeed
        dataStore.sizeCellsPerWidth = size.toInt()

        speedMutableLiveData.postValue(dataStore.speedToShow)
        sizeMutableLiveData.postValue(dataStore.sizeCellsPerWidth)

        dataStore.cellsField = CellsField(dataStore.sizeCellsPerWidth, dataStore.randomAliveFactor)
        dataStore.isWorking = true
    }

    fun onSpeedChanged(edt: EditText): TextWatcher {
        return textWatcher(edt, dataStore.maxSpeedValue)
    }

    fun onSizeChanged(edt: EditText): TextWatcher {
        return textWatcher(edt, dataStore.maxSizeValue)
    }

    private fun textWatcher(edt: EditText, maxValue: Int) = object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            if (editable.startsWith("0")) {
                edt.setText("")
            }
            if (editable.toString() != "" && editable.toString().toDouble() > maxValue) {
                edt.setText(maxValue.toString())
                edt.setSelection(edt.text.length)
            }
        }

        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
        }
    }
}