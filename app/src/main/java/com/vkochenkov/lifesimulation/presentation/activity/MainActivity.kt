package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.presentation.view.FieldView
import com.vkochenkov.lifesimulation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var gameBackgroundLayout: LinearLayout
    private lateinit var fieldView: FieldView
    private lateinit var restartBtn: Button
    private lateinit var startSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewsById()
        createFieldView()
        setOnClickListeners()
        observeViewModelEvents()

        mainViewModel.onCreate()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.onStart()
    }

    override fun onStop() {
        super.onStop()
        mainViewModel.onStop()
    }

    private fun createFieldView() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val phoneScreenWidth = metrics.widthPixels
        fieldView = FieldView(this, phoneScreenWidth)
        val params = LinearLayout.LayoutParams(phoneScreenWidth, phoneScreenWidth)
        params.gravity = Gravity.CENTER_HORIZONTAL
        fieldView.layoutParams = params
        gameBackgroundLayout.addView(fieldView, 0)
    }

    private fun findViewsById() {
        gameBackgroundLayout = findViewById(R.id.field_background)
        restartBtn = findViewById(R.id.btn_restart)
        startSwitch = findViewById(R.id.switch_start)
    }

    private fun setOnClickListeners() {
        restartBtn.setOnClickListener {
            mainViewModel.onRestartFromBtn()
        }

        startSwitch.setOnClickListener {
            mainViewModel.onStartStopSwitch(startSwitch.isChecked)
        }
    }

    private fun observeViewModelEvents() {

        mainViewModel.cellsFieldLiveData.observe(this, Observer {
            it?.let {
                fieldView.cellsField = it
                fieldView.invalidate()
            }
        })

        mainViewModel.isWorkingLiveData.observe(this, Observer {
            it?.let {
                startSwitch.isChecked = it
            }
        })
    }
}