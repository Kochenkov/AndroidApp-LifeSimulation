package com.vkochenkov.lifesimulation.model

object DataStore {

    //default values
    var cellsAmountPerWidth = 51
    var randomAliveFactor = 0.5
    val minRenderingSpeed = 1000L //toShown = 1000/1000 = 1 g/s

    var renderingSpeed = minRenderingSpeed

    var cellsField: CellsField? = null
}