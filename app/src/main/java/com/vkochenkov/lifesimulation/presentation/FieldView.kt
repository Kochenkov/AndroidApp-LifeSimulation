package com.vkochenkov.lifesimulation.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.log


class FieldView : View {

    var viewSize: Int = 0

    var cellsArray: Array<Array<Boolean>>? = null

    constructor(ctx: Context) : super(ctx) {
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
    }

    companion object {
        val BACKGROUND_COLOR = Color.BLUE
        val CELLS_COLOR = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewSize = this.measuredWidth
        drawBackground(canvas)
        if (cellsArray!=null) {
            drawCells(canvas, cellsArray!!)
        }

    }

    private fun drawBackground(canvas: Canvas) {
        val paint = Paint()
        paint.color = BACKGROUND_COLOR
        val rect = Rect(0, 0, viewSize, viewSize)
        canvas.drawRect(rect, paint)
    }

    private fun drawCells(canvas: Canvas, cellsArray: Array<Array<Boolean>>) {
        val paint = Paint()
        paint.color = CELLS_COLOR

        val cellsAmount = cellsArray[0].size
        Log.d("cellsAmount", cellsAmount.toString())
        val rectSize = viewSize/cellsAmount
        Log.d("rectSize", rectSize.toString())
        Log.d("viewSize", viewSize.toString())

        var line = 0
        var column = 0

        for (array in cellsArray) {
           // Log.d("line", line.toString())
            for (elem in array) {
                val rect = Rect(column, line, rectSize+column, rectSize+line)
                column+=rectSize
                //Log.d("column", column.toString())

                if (elem) {
                    canvas.drawRect(rect, paint)
                }
            }
            line+=rectSize

            column = 0
        }



    }
}