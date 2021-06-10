package com.vkochenkov.lifesimulation.model

import java.io.Serializable

class CellsField constructor(val size: Int, private val randomAliveFactor: Double) : Serializable {

    var generationCount: Long = 0L

    var cellsArray: Array<Array<Cell?>> = Array(size) { arrayOfNulls<Cell>(size) }

    init {
        for (i in 0 until size) {
            for (j in 0 until size) {
                val cell = Cell(i, j)
                cell.isAlive = Math.random() < randomAliveFactor
                cellsArray[i][j] = cell
            }
        }
    }

    //поиск живых соседей для всех клеток
    fun findAllNeighbors() {
        for (array in cellsArray) {
            for (element in array) {
                val cell: Cell = element!!
                cell.countAliveNeighbors(cellsArray)
            }
        }
    }

    //выставление флага, на основании количества живых соседей для всех клеток
    fun determineDeadOrAlive() {
        generationCount++
        for (array in cellsArray) {
            for (element in array) {
                val cell: Cell = element!!
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
    }
}