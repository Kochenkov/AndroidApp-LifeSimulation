package com.vkochenkov.lifesimulation.model

object DataStore {

    //default values
    val baseCellsAmountPerWidth: Int = 50
    val baseRenderingSpeed: Double = 250.0

    var randomAliveFactor = 0.5

    var sizeCellsPerWidth: Int = baseCellsAmountPerWidth
    var renderingSpeed: Double = baseRenderingSpeed

    var cellsField: CellsField? = null
}