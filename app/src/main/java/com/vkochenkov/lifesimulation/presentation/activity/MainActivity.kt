package com.vkochenkov.lifesimulation.presentation.activity

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
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

    lateinit var gameBackgroundLayout: LinearLayout
    lateinit var fieldView: FieldView
    lateinit var restartBtn: Button
    lateinit var stopBtn: Button

    lateinit var disposable: CompositeDisposable

    lateinit var cellsField: CellsField

    //todo вынести
    val size = 51
    val randomAliveFactor = 0.5
    val renderInterval = 250L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewsById()
        createFieldView()
        initFields()
        setOnClickListeners()
        startObserve()
    }

    private fun createFieldView() {
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val phoneScreenWidth = metrics.widthPixels
        fieldView = FieldView(this, phoneScreenWidth)
        val params = LinearLayout.LayoutParams(phoneScreenWidth, phoneScreenWidth)
        params.gravity = Gravity.CENTER_HORIZONTAL
        fieldView.layoutParams = params
        gameBackgroundLayout.addView(fieldView, 0)
    }

    private fun findViewsById() {
        gameBackgroundLayout = findViewById(R.id.field_background)
        restartBtn = findViewById(R.id.btn_restart)
        stopBtn = findViewById(R.id.btn_stop)
    }

    private fun startObserve() {
        val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer(true))
    }

    private fun setOnClickListeners() {
        var count = 1

        restartBtn.setOnClickListener {
            disposable.clear()
            val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer(true))
        }

        //todo костыльная обработка для теста, переписать!
        stopBtn.setOnClickListener {
            count++
            disposable.clear()
            if (count%2==0) {
                stopBtn.text = "start"
            } else {
                val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer(false))
                stopBtn.text = "stop"
            }
        }
    }

    private fun initFields() {
        disposable = CompositeDisposable()
        cellsField = CellsField(size, randomAliveFactor)
    }

    private fun observer(recreateCellsArray: Boolean) = object : Observer<Long> {
            override fun onComplete() {}

            override fun onSubscribe(d: Disposable) {
                if (recreateCellsArray) {
                    cellsField = CellsField(size, randomAliveFactor)
                }
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