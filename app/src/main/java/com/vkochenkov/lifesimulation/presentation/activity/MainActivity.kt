package com.vkochenkov.lifesimulation.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
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
    private lateinit var generationsTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewsById()
        createFieldView()
        setOnClickListeners()
        observeViewModelEvents()
    }

    override fun onStart() {
        super.onStart()
        mainViewModel.onStart()
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.onPose()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_exit -> onBackPressed()
            R.id.menu_item_settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
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
        generationsTv = findViewById(R.id.tv_dinamic_generation)
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

        mainViewModel.generationLiveData.observe(this, Observer {
            it?.let {
                generationsTv.text = it.toString()
            }
        })
    }
}