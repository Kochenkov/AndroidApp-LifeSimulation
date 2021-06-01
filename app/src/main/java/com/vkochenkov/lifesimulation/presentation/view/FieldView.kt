package com.vkochenkov.lifesimulation.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.model.Cell


class FieldView : View {

    var viewSize: Int = 0

    var toDrawArray: Array<Array<Cell?>>? = null

    val BACKGROUND_COLOR: Int
    val CELLS_COLOR: Int

    constructor(ctx: Context) : super(ctx) {}

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {}

    init {
        val typedValue = TypedValue()
        val theme = context.theme

        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        BACKGROUND_COLOR = typedValue.data

        theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        CELLS_COLOR = typedValue.data
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        viewSize = this.measuredWidth
        drawBackground(canvas)
        if (toDrawArray != null) {
            drawCells(canvas, toDrawArray!!)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        val paint = Paint()
        paint.color =
            BACKGROUND_COLOR
        val rect = Rect(0, 0, viewSize, viewSize)
        canvas.drawRect(rect, paint)
    }

    private fun drawCells(canvas: Canvas, cellsArray: Array<Array<Cell?>>) {
        val paint = Paint()
        paint.color =
            CELLS_COLOR

        val cellsAmount = cellsArray[0].size
        val rectSize = viewSize / cellsAmount

        var line = 0
        var column = 0

        for (array in cellsArray) {
            for (elem in array) {
                val rect = Rect(column, line, rectSize + column, rectSize + line)
                column += rectSize
                if (elem!!.isAlive) {
                    canvas.drawRect(rect, paint)
                }
            }
            line += rectSize
            column = 0
        }
    }
}