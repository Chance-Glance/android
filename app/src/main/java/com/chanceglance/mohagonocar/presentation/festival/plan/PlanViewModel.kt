package com.chanceglance.mohagonocar.presentation.festival.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar

class PlanViewModel:ViewModel() {
    private val _selectedDate = MutableLiveData<LocalDate>() // 년, 월, 일 데이터를 저장
    val selectedDate: LiveData<LocalDate> = _selectedDate

    private val _festivalId = MutableLiveData<Int>()
    val festivalId:MutableLiveData<Int> get() = _festivalId

    private val _selectedDepartTime = MutableLiveData<LocalTime>() // 출발 시간 저장 (hour, minute)
    val selectedDepartTime: LiveData<LocalTime> = _selectedDepartTime

    private val _selectedArrivalTime = MutableLiveData<LocalTime>() // 도착 시간 저장 (hour, minute)
    val selectedArrivalTime: LiveData<LocalTime> = _selectedArrivalTime

    fun getDate(year: Int, month: Int, day: String) {
        _selectedDate.value = LocalDate.of(year, month+1, day.toInt())
    }

    fun getFestivalId(id:Int){
        _festivalId.value=id
    }

    fun setDepartTime(hour: Int, minute: Int) {
        _selectedDepartTime.value = LocalTime.of(hour, minute)
    }

    fun setArrivalTime(hour: Int, minute: Int) {
        _selectedArrivalTime.value = LocalTime.of(hour, minute)
    }
}