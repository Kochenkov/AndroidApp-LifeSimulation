package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vkochenkov.lifesimulation.R
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.presentation.view.FieldView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    
    //todo написать презентер

    lateinit var fieldView: FieldView
    lateinit var restartBtn: Button
    lateinit var stopBtn: Button

    lateinit var observer: Observer<Long>
    lateinit var disposable: CompositeDisposable

    lateinit var cellsField: CellsField

    //todo вынести
    val size = 51
    val randomAliveFactor = 0.5
    val renderInterval = 250L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initFields()
        setOnClickListeners()
        startObserve()
    }

    private fun startObserve() {
        val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    private fun setOnClickListeners() {

        restartBtn.setOnClickListener {
            disposable.clear()
            val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
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

        //todo вынести в ф-цию и передавать в нее параметр генерации нового массива, либо использования старого для продолжения
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