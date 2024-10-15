package com.chanceglance.mohagonocar.extension

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto

sealed class NearbyPlaceState {
    data object Loading:NearbyPlaceState()
    data class Success(val data: ResponseNearbyPlaceDto):NearbyPlaceState()
    data class Error(val message:String):NearbyPlaceState()
}