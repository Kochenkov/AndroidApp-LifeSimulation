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

        fieldView.toDrawArray = cellsField.cellsArray

        mainThread().start()

    }

    inner class mainThread : Thread() {
        override fun run() {
            super.run()

            while (true) {

                //todo вынести в отдельные методы?

                //поиск соседей
                for (i in 0 until cellsField.size) {
                    for (j in 0 until cellsField.size) {
                        val cell: Cell = cellsField.cellsArray[i][j]!!
                        cell.countAliveNeighbors(cellsField.cellsArray)
                    }
                }

                //жив или мертв
                for (i in 0 until cellsField.size) {
                    for (j in 0 until cellsField.size) {
                        val cell: Cell = cellsField.cellsArray[i][j]!!
                        if (cell.isAlive) {
                            if (cell.aliveNeighbors < 2 || cell.aliveNeighbors > 3) {
                                cell.isAlive = false
                            }
                        } else {
                            if (cell.aliveNeighbors == 3) {
                                cell.isAlive = true
                            }
                        }
                    }
                }
                fieldView.toDrawArray = cellsField.cellsArray
                fieldView.invalidate()
                sleep(500)
            }
        }
    }
}