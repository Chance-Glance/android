package com.chanceglance.mohagonocar.domain.repository

import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto

interface KakaoRepository {
    suspend fun getAddress(
        token:String,
        address:String
    ):Result<ResponseAddressDto>
}