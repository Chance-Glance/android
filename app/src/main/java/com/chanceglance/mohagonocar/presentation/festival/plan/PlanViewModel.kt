package com.chanceglance.mohagonocar.presentation.festival.plan

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import java.util.Calendar

class PlanViewModel:ViewModel() {
    var selectedYear = Calendar.YEAR
    var selectedMonth = Calendar.MONTH
    var selectedDay = Calendar.DAY_OF_MONTH

    fun getDate(year:Int, month:Int, day:String){
        selectedYear= year
        selectedMonth=month
        selectedDay=day.toInt()
    }
}