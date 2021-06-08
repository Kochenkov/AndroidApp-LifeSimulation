package com.vkochenkov.lifesimulation.model

object DataStore {

    /** generations per second */
    val maxSpeedValue: Int = 100
    /** cells per screen */
    val maxSizeValue: Int = 150
    /** cells per screen */
    val baseCellsAmountPerWidth: Int = 50
    /** milliseconds */
    val baseRenderingSpeed: Double = 250.0
    /**percents*/
    var randomAliveFactor = 0.5

    var sizeCellsPerWidth: Int = baseCellsAmountPerWidth
    var renderingSpeed: Double = baseRenderingSpeed
    var speedToShow: Double = 1000/renderingSpeed

    var cellsField: CellsField? = null
}