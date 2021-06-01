package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.model.Cell
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.presentation.view.FieldView

class MainActivity : AppCompatActivity() {

    lateinit var fieldView: FieldView

    val size = 51
    val randomAliveFactor = 0.5

    val cellsField = CellsField(size, randomAliveFactor)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fieldView = findViewById(R.id.field_view)

        fieldView.cellsField = cellsField

        mainThread().start()

    }

    inner class mainThread : Thread() {
        override fun run() {
            super.run()

            while (true) {

                //todo вынести в отдельные методы?

                //поиск соседей
                cellsField.findAllNeighbors()
                cellsField.determineDeadOrAlive()

                fieldView.cellsField = cellsField
                fieldView.invalidate()
                sleep(250)
            }
        }
    }
}