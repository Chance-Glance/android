package com.chanceglance.mohagonocar.data.service

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthService {

    @GET("/api/v1/festivals")
    suspend fun getFesitvals(
        @Query("page") page:Int,
        @Query("size") size:Int,
    ):ResponseFestivalDto
}