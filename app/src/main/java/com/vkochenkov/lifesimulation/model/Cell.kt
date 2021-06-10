package com.vkochenkov.lifesimulation.model

class Cell(val verticalPosition: Int, val horizontalPosition: Int) {

    var isAlive = false
    var aliveNeighbors = 0

    fun countAliveNeighbors(
        array: Array<Array<Cell?>>
    ) {
        var count = 0
        val x = this.verticalPosition
        val y = this.horizontalPosition
        if (x != 0 && y != 0 && x != array.size - 1 && y != array.size - 1) {
            for (i in x - 1..x + 1) {
                for (j in y - 1..y + 1) {
                    if (i!=x || j!=y) {
                        val neighbor = array[i][j]
                        if (neighbor!!.isAlive) {
                            count++
                        }
                    }
                }
            }
        }
        aliveNeighbors = count
    }
}