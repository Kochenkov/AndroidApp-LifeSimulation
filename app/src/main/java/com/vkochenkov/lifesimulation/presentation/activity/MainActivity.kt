package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.presentation.view.FieldView
import com.vkochenkov.lifesimulation.presentation.presenter.MainPresenter

class MainActivity : AppCompatActivity() {

    private val presenter = MainPresenter

    lateinit var gameBackgroundLayout: LinearLayout
    lateinit var fieldView: FieldView
    lateinit var restartBtn: Button
    lateinit var stopBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewsById()
        createFieldView()
        setOnClickListeners()

        presenter.onCreate(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
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
        stopBtn = findViewById(R.id.btn_stop)
    }

    private fun setOnClickListeners() {
        restartBtn.setOnClickListener {
            presenter.restart()
        }

        stopBtn.setOnClickListener {
            presenter.stop()
        }
    }


}