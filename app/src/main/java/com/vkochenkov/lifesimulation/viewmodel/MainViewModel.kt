package com.vkochenkov.lifesimulation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkochenkov.lifesimulation.model.CellsField
import com.vkochenkov.lifesimulation.model.DataStore
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    var dataStore = DataStore
    var disposable = CompositeDisposable()

    private var mutableCellsFieldLiveData = MutableLiveData<CellsField>()
    private var mutableIsWorkingLiveData = MutableLiveData<Boolean>()

    val cellsFieldLiveData: LiveData<CellsField> = mutableCellsFieldLiveData
    val isWorkingLiveData: LiveData<Boolean> = mutableIsWorkingLiveData

    fun onCreate() {
        startObserveField(true)
    }

    fun onStart() {
        startObserveField(false)
    }

    fun onStop() {
        disposable.clear()
        mutableIsWorkingLiveData.postValue(false)
    }

    fun onRestartFromBtn() {
        disposable.clear()
        mutableIsWorkingLiveData.postValue(false)
        startObserveField(true)
    }

    fun onStartStopSwitch(isChecked: Boolean) {
        if (!isChecked) {
            disposable.clear()
            mutableIsWorkingLiveData.postValue(false)
        } else {
            startObserveField(false)
        }
    }

    private fun startObserveField(recreateCellsArray: Boolean) {
        val fieldObservable = Observable.interval(dataStore.renderInterval, TimeUnit.MILLISECONDS)
        fieldObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fieldObserver(recreateCellsArray))
    }

    private fun fieldObserver(recreateCellsArray: Boolean) = object : Observer<Long> {

        override fun onComplete() {}

        override fun onSubscribe(d: Disposable) {
            if (recreateCellsArray) {
                dataStore.cellsField = CellsField(dataStore.size, dataStore.randomAliveFactor)
            }
            disposable.add(d)
            mutableIsWorkingLiveData.postValue(true)
        }

        override fun onNext(t: Long) {
            dataStore.cellsField?.findAllNeighbors()
            dataStore.cellsField?.determineDeadOrAlive()
            mutableCellsFieldLiveData.postValue(dataStore.cellsField)
        }

        override fun onError(e: Throwable) {}
    }
}