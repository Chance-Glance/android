package com.chanceglance.mohagonocar.presentation.festival.plan.time

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimeViewModel:ViewModel() {
    private val _decideDepart = MutableLiveData<Boolean>() // 년, 월, 일 데이터를 저장
    private val _decideArrival = MutableLiveData<Boolean>()

    val decideDepart: LiveData<Boolean> = _decideDepart
    val decideArrival: LiveData<Boolean> = _decideArrival



    fun getDepart(decide:Boolean){
        _decideDepart.value=decide
    }
    fun getArrival(decide: Boolean){
        _decideArrival.value=decide
    }


}