package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.viewmodel.SettingsViewModel

class SettingsActivity : AppCompatActivity() {

    lateinit var speedEdt: EditText
    lateinit var saveBtn: Button

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
        saveBtn = findViewById(R.id.settings_btn_save)
    }

    private fun setOnClickListeners() {
        saveBtn.setOnClickListener {
            settingsViewModel.onSaveBtnClicked(this)
        }

        speedEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable) {
                if (editable.startsWith("0")) {
                    speedEdt.setText("")
                }
                if (editable.toString() != "" && editable.toString().toDouble() > 999) {
                    speedEdt.setText("999")
                }
            }

            override fun beforeTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeViewModelEvents() {
        settingsViewModel.speedLiveData.observe(this, Observer {
            it?.let {
                speedEdt.setText(it.toString())
            }
        })
    }
}