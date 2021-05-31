package com.vkochenkov.lifesimulation.model

import junit.framework.Assert.assertEquals
import org.junit.Test

class CellTests {

    @Test
    fun countNeighborsTest() {

        val cellsField = CellsField(5);
        for (i in cellsField.cellsArray) {
            for (j in i) {
                j!!.isAlive = true
            }
        }
        val cell = cellsField.cellsArray[2][2]
        cell!!.countNeighbors(cellsField.cellsArray)

        assertEquals(8, cell.aliveNeighbors)
    }
}