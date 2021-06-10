package com.vkochenkov.lifesimulation.presentation.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.model.CellsField

class FieldView : View {

    var viewSize: Int = 0

    var cellsField: CellsField? = null

    val BACKGROUND_COLOR: Int
    val CELLS_COLOR: Int

    constructor(ctx: Context) : super(ctx) {}

    constructor(ctx: Context, viewSize: Int) : super(ctx) {
        this.viewSize = viewSize
    }

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {}

    init {
        val typedValue = TypedValue()
        val theme = context.theme

        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
        BACKGROUND_COLOR = typedValue.data

        theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
        CELLS_COLOR = typedValue.data
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (viewSize == 0) {
            viewSize = this.measuredWidth
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        if (cellsField != null) {
            drawCells(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event!!.x
        val y = event.y
        makeCellAlive(x, y)
        invalidate()
        return true
    }

    private fun makeCellAlive(x: Float, y: Float) {
        val rectSize = viewSize / cellsField!!.size
        loop@ for (array in cellsField!!.cellsArray) {
            for (elem in array) {
                if ((elem!!.verticalPosition*rectSize+rectSize)>=y && (elem.horizontalPosition*rectSize+rectSize)>=x) {
                    elem.isAlive = true
                    break@loop
                }
            }
        }
    }

    private fun drawBackground(canvas: Canvas) {
        val paint = Paint()
        paint.color = BACKGROUND_COLOR
        val rect = Rect(0, 0, viewSize, viewSize)
        canvas.drawRect(rect, paint)
    }

    private fun drawCells(canvas: Canvas) {
        val paint = Paint()
        paint.color = CELLS_COLOR

        val cellsAmount = cellsField!!.size
        val rectSize = viewSize / cellsAmount

        val difSize = viewSize - rectSize*cellsAmount + 1

        var line = difSize/2
        var column = difSize/2

        for (array in cellsField!!.cellsArray) {
            for (elem in array) {
                val rect = Rect(column, line, rectSize + column, rectSize + line)
                column += rectSize
                if (elem!!.isAlive) {
                    canvas.drawRect(rect, paint)
                }
            }
            line += rectSize
            column = difSize/2
        }
    }
}