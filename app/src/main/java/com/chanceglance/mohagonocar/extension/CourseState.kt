package com.chanceglance.mohagonocar.extension

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto

sealed class CourseState {
    data object Loading:CourseState()
    data class Success(val data: ResponseTravelCourseDto):CourseState()
    data class Error(val message:String):CourseState()
}