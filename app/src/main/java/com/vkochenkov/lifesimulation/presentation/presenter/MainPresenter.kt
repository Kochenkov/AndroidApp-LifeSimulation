package com.vkochenkov.lifesimulation.presentation.presenter

import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.presentation.activity.MainActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

object MainPresenter {

    //todo вынести?
    val size = 51
    val randomAliveFactor = 0.5
    val renderInterval = 250L

    private var activity: MainActivity? = null

    private var disposable = CompositeDisposable()

    private var cellsField = CellsField(size, randomAliveFactor)

    fun onCreate(activity: MainActivity) {
        this.activity = activity
        startObserve()
    }

    fun onStart(activity: MainActivity) {
        if (this.activity == null) {
            this.activity = activity
        }
    }

    fun onStop() {
        this.activity = null
        disposable.clear()
    }

    fun restart() {
        disposable.clear()
        startObserve()
    }

    fun stop() {
        //todo доработать start/stop
        disposable.clear()
//        val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
//        observable.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(observer(false))
    }

    private fun startObserve() {
        val observable = Observable.interval(renderInterval, TimeUnit.MILLISECONDS)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer(true))
    }

    private fun observer(recreateCellsArray: Boolean) = object : Observer<Long> {
        override fun onComplete() {}

        override fun onSubscribe(d: Disposable) {
            if (recreateCellsArray) {
                cellsField = CellsField(size, randomAliveFactor)
            }
            activity?.fieldView?.cellsField = cellsField
            disposable.add(d)
        }

        override fun onNext(t: Long) {
            cellsField.findAllNeighbors()
            cellsField.determineDeadOrAlive()

            activity?.fieldView?.cellsField = cellsField
            activity?.fieldView?.invalidate()
        }

        override fun onError(e: Throwable) {}
    }
}