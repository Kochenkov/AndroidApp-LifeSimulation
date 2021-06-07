package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    private lateinit var speedEdt: EditText
    private lateinit var sizeEdt: EditText
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

    override fun onStart() {
        super.onStart()
        settingsViewModel.onStart()
    }


    override fun onStop() {
        super.onStop()
        settingsViewModel.onStop()
    }

    private fun findViewsById() {
        speedEdt = findViewById(R.id.settings_edt_speed)
        sizeEdt = findViewById(R.id.settings_edt_size)
        saveBtn = findViewById(R.id.settings_btn_save)
    }

    private fun setOnClickListeners() {
        saveBtn.setOnClickListener {
            settingsViewModel.onSaveBtnClicked(speedEdt.text.toString(), sizeEdt.text.toString())
            onBackPressed()
        }

        speedEdt.addTextChangedListener(settingsViewModel.onSpeedChanged(speedEdt))
        sizeEdt.addTextChangedListener(settingsViewModel.onSizeChanged(sizeEdt))
    }

    private fun observeViewModelEvents() {
        settingsViewModel.speedLiveData.observe(this, Observer {
            it?.let {
                speedEdt.setText(it.toString())
            }
        })

        settingsViewModel.sizeLiveData.observe(this, Observer {
            it?.let {
                sizeEdt.setText(it.toString())
            }
        })
    }
}