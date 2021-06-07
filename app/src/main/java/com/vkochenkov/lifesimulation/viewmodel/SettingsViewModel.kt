package com.vkochenkov.lifesimulation.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
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
        dataStore.speedToShow = 1000.0/dataStore.renderingSpeed
        dataStore.sizeCellsPerWidth = activity.sizeEdt.text.toString().toInt()

        mutableSpeedLiveData.postValue(dataStore.speedToShow)
        mutableSizeLiveData.postValue(dataStore.sizeCellsPerWidth)

        dataStore.cellsField = CellsField(dataStore.sizeCellsPerWidth, dataStore.randomAliveFactor)

        activity.onBackPressed()
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

        override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
    }
}