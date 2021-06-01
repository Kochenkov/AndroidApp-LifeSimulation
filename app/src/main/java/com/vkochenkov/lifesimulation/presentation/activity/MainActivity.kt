package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.presentation.view.FieldView

class MainActivity : AppCompatActivity() {

    lateinit var fieldView: FieldView
    lateinit var restartBtn: Button
    lateinit var stopBtn: Button

    var gameThreadIsWorking = false
    val gameThreadIsStoppedKey = "gameThreadIsWorking"

    //todo вынести в конфиг/базу
    val size = 51
    val randomAliveFactor = 0.5

    lateinit var cellsField: CellsField
    val cellsFieldKey = "cellsField"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        cellsField = CellsField(size, randomAliveFactor)
        if (!gameThreadIsWorking) {
            startNewGameThread()
        }

        restartBtn.setOnClickListener {
            gameThreadIsWorking = false
            startNewGameThread()
        }

        stopBtn.setOnClickListener {
            gameThreadIsWorking = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(gameThreadIsStoppedKey, gameThreadIsWorking)
        outState.putSerializable(cellsFieldKey, cellsField)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        gameThreadIsWorking = savedInstanceState.getBoolean(gameThreadIsStoppedKey)
        cellsField = savedInstanceState.getSerializable(cellsFieldKey) as CellsField
    }

    private fun startNewGameThread() {
        fieldView.cellsField = cellsField
        mainThread().start()
    }

    private fun initFields() {
        fieldView = findViewById(R.id.field_view)
        restartBtn = findViewById(R.id.btn_restart)
        stopBtn = findViewById(R.id.btn_stop)
    }

    inner class mainThread : Thread() {
        override fun run() {
            gameThreadIsWorking = true
            while (gameThreadIsWorking) {

                cellsField.findAllNeighbors()
                cellsField.determineDeadOrAlive()

                fieldView.cellsField = cellsField
                //работает на бэкграунд потоке:)
                fieldView.invalidate()
                sleep(250)
            }
        }
    }
}