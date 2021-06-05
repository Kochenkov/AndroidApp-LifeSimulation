package com.vkochenkov.lifesimulation.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var speedEdt: EditText
    private lateinit var saveBtn: Button

    private val settingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewsById()
        setOnClickListeners()
        observeViewModelEvents()
    }

    private fun findViewsById() {
        speedEdt = findViewById(R.id.settings_edt_speed)
        saveBtn = findViewById(R.id.settings_btn_save)
    }

    private fun setOnClickListeners() {
        saveBtn.setOnClickListener {
            settingsViewModel.onSaveBtnClicked()
        }
    }

    private fun observeViewModelEvents() {

        settingsViewModel.speedLiveData.observe(this, Observer {
            it?.let {
                speedEdt.setText(it.toString())
            }
        })
    }
}