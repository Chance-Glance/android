package com.chanceglance.mohagonocar.data.service

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthService {

    @GET("/api/v1/festivals")
    suspend fun getFesitvals(
        @Query("page") page:Int,
        @Query("size") size:Int,
    ):ResponseFestivalDto

    @GET("/api/v1/nearby-places/{festivalId}")
    suspend fun getNearbyPlaces(
        @Path("festivalId") festivalId:Int,
        @Query("page") page:Int,
        @Query("size") size:Int,
    ):ResponseNearbyPlaceDto
}