package com.vkochenkov.lifesimulation.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.lifesimulation.R

class MainActivity : AppCompatActivity() {
    lateinit var fieldView: FieldView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fieldView = findViewById(R.id.field_view)

        var cellsArray: Array<Array<Boolean>> = arrayOf(
            arrayOf(true, false, true, false, true, false),
            arrayOf(false, true, false, true, false, true),
            arrayOf(true, false, true, false, true, false),
            arrayOf(false, true, false, true, false, true),
            arrayOf(true, false, true, false, true, false)
        )

        //todo пнять почему не робит
        fieldView.cellsArray = cellsArray


    }
}