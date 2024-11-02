package com.chanceglance.mohagonocar.presentation.festival.plan.calendar

import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView

class ScheduleViewModel :ViewModel(){
    var selectedPosition: Int = RecyclerView.NO_POSITION // 선택된 날짜 위치 저장

}