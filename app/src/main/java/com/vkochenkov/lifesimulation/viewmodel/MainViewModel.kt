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

    private var cellsFieldMutableLiveData = MutableLiveData<CellsField>()
    private var isWorkingMutableLiveData = MutableLiveData<Boolean>()
    private var generationsMutableLiveData = MutableLiveData<Long>()

    val cellsFieldLiveData: LiveData<CellsField> = cellsFieldMutableLiveData
    val isWorkingLiveData: LiveData<Boolean> = isWorkingMutableLiveData
    val generationLiveData: LiveData<Long> = generationsMutableLiveData

    fun onStart() {
        if (dataStore.isWorking) {
            startObserveField(false)
        }
    }

    fun onPose() {
        disposable.clear()
        isWorkingMutableLiveData.postValue(false)
    }

    fun onRestartFromBtn() {
        disposable.clear()
        dataStore.isWorking = false
        isWorkingMutableLiveData.postValue(dataStore.isWorking)
        startObserveField(true)
    }

    fun onClearFromBtn() {
        disposable.clear()
        dataStore.isWorking = false
        isWorkingMutableLiveData.postValue(dataStore.isWorking)
        dataStore.cellsField = CellsField(dataStore.sizeCellsPerWidth, 0.0)
        cellsFieldMutableLiveData.postValue(dataStore.cellsField)
        generationsMutableLiveData.postValue(dataStore.cellsField?.generationCount)
    }

    fun onStartStopSwitch(isChecked: Boolean) {
        if (!isChecked) {
            disposable.clear()
            dataStore.isWorking = false
            isWorkingMutableLiveData.postValue(dataStore.isWorking)
        } else {
            startObserveField(false)
        }
    }

    private fun startObserveField(recreateCellsArray: Boolean) {
        val fieldObservable = Observable.interval(dataStore.renderingSpeed.toLong(), TimeUnit.MILLISECONDS)
        fieldObservable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(fieldObserver(recreateCellsArray))
    }

    private fun fieldObserver(recreateCellsArray: Boolean) = object : Observer<Long> {

        override fun onComplete() {}

        override fun onSubscribe(d: Disposable) {
            if (recreateCellsArray || dataStore.cellsField == null) {
                dataStore.cellsField = CellsField(dataStore.sizeCellsPerWidth, dataStore.randomAliveFactor)
            }
            disposable.add(d)
            dataStore.isWorking = true
            isWorkingMutableLiveData.postValue(dataStore.isWorking)
        }

        override fun onNext(t: Long) {
            dataStore.cellsField?.findAllNeighbors()
            dataStore.cellsField?.determineDeadOrAlive()
            cellsFieldMutableLiveData.postValue(dataStore.cellsField)
            generationsMutableLiveData.postValue(dataStore.cellsField?.generationCount)
        }

        override fun onError(e: Throwable) {}
    }
}