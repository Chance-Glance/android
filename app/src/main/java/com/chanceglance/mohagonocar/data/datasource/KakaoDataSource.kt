package com.chanceglance.mohagonocar.data.datasource

import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoDataSource {
    suspend fun getAddress(
        @Header("Authorization") token:String,
        @Query("query") address:String
    ): ResponseAddressDto
}