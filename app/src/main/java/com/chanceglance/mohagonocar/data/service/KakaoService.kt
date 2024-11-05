package com.chanceglance.mohagonocar.data.service

import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoMapService {
    @GET("local/search/keyword.json")
    suspend fun getAddress(
        @Header("Authorization") token :String,
        @Query("query") address: String
    ): ResponseAddressDto
}