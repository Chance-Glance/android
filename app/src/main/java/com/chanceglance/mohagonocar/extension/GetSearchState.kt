package com.chanceglance.mohagonocar.extension

import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto

sealed class GetSearchState {
    data object Loading:GetSearchState()
    data class Success(val data: ResponseAddressDto):GetSearchState()
    data class Error(val message:String):GetSearchState()
}