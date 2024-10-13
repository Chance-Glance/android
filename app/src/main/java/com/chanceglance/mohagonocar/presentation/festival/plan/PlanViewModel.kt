package com.chanceglance.mohagonocar.presentation.festival.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import java.util.Calendar

class PlanViewModel:ViewModel() {
    private val _selectedDate = MutableLiveData<Triple<Int, Int, Int>>() // 년, 월, 일 데이터를 저장
    val selectedDate: LiveData<Triple<Int, Int, Int>> = _selectedDate

    fun getDate(year: Int, month: Int, day: String) {
        _selectedDate.value = Triple(year, month, day.toInt())
    }
}