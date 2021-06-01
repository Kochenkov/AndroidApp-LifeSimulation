package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.presentation.view.FieldView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var fieldView: FieldView
    lateinit var restartBtn: Button
    lateinit var stopBtn: Button

    lateinit var observer: Observer<Long>
    lateinit var disposable: CompositeDisposable

    lateinit var cellsField: CellsField

    //todo вынести
    val size = 51
    val randomAliveFactor = 0.5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        setOnClickListeners()
        startObserve()
    }

    private fun startObserve() {
        val observable = Observable.interval(250, TimeUnit.MILLISECONDS)
        observable.subscribe(observer)
    }

    private fun setOnClickListeners() {
        restartBtn.setOnClickListener {
            val observable = Observable.interval(250, TimeUnit.MILLISECONDS)
            observable.subscribe(observer)
        }

        stopBtn.setOnClickListener {
            disposable.clear()
        }
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putBoolean(gameThreadIsStoppedKey, gameThreadIsWorking)
//        outState.putSerializable(cellsFieldKey, cellsField)
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//        gameThreadIsWorking = savedInstanceState.getBoolean(gameThreadIsStoppedKey)
//        cellsField = savedInstanceState.getSerializable(cellsFieldKey) as CellsField
//    }

    private fun initFields() {
        fieldView = findViewById(R.id.field_view)
        restartBtn = findViewById(R.id.btn_restart)
        stopBtn = findViewById(R.id.btn_stop)

        disposable = CompositeDisposable()
        cellsField = CellsField(size, randomAliveFactor)

        observer = object : Observer<Long> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {
                cellsField = CellsField(size, randomAliveFactor)
                fieldView.cellsField = cellsField
                disposable.add(d)
            }

            override fun onNext(t: Long) {
                fieldView.cellsField = cellsField
                cellsField.findAllNeighbors()
                cellsField.determineDeadOrAlive()
                fieldView.invalidate()
            }

            override fun onError(e: Throwable) {}
        }
    }
}