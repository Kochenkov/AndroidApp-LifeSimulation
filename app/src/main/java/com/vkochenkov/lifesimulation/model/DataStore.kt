package com.vkochenkov.lifesimulation.model

object DataStore {

    //default values
    var cellsAmountPerWidth = 51
    var randomAliveFactor = 0.5
    val minRenderingSpeed: Double = 1000.0

    var renderingSpeed: Double = minRenderingSpeed

    var cellsField: CellsField? = null
}