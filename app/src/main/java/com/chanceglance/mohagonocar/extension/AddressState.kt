package com.chanceglance.mohagonocar.extension

import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto

sealed class AddressState {
    data object Loading:AddressState()
    data class Success(val data: ResponseAddressDto):AddressState()
    data class Error(val message:String):AddressState()
}