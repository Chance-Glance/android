package com.chanceglance.mohagonocar.extension

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto

sealed class FestivalState {
    data object Loading:FestivalState()
    data class Success(val data:ResponseFestivalDto):FestivalState()
    data class Error(val message:String):FestivalState()
}