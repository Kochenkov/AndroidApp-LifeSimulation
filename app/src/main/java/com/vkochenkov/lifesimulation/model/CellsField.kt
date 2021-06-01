package com.vkochenkov.lifesimulation.model

import java.io.Serializable

class CellsField constructor(val size: Int): Serializable {

    var randomAliveFactor: Double = 0.5

    val cellsArray: Array<Array<Cell?>> = Array(size) { arrayOfNulls<Cell>(size) }

    constructor(size:Int, randomAliveFactor: Double): this(size) {
        CellsField(size)
        this.randomAliveFactor = randomAliveFactor
    }

//    companion object {
//        fun toBooleanArray(inputArray: Array<Array<Cell?>>): Array<Array<Boolean?>> {
//            val outputArray = Array(inputArray.size) { arrayOfNulls<Boolean>(inputArray.size)}
//            for (i in inputArray.indices) {
//                for (j in inputArray.indices) {
//                    outputArray[i][j] = inputArray[i][j]?.isAlive
//                }
//            }
//            return outputArray
//        }
//
//        fun toCellArray(inputArray: Array<Array<Boolean?>>): Array<Array<Cell?>> {
//            val outputArray = Array(inputArray.size) { arrayOfNulls<Cell>(inputArray.size)}
//            for (i in inputArray.indices) {
//                for (j in inputArray.indices) {
//                    val cell = Cell(i, j)
//                    if (inputArray[i][j]!!) {
//                        cell.isAlive = true
//                    }
//                }
//            }
//            return outputArray
//        }
//    }

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