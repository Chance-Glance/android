package com.chanceglance.mohagonocar.data.datasource

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto

interface AuthDataSource {
    suspend fun getFestival(
        page:Int,
        size:Int,
    ):ResponseFestivalDto

    suspend fun getNearbyPlace(
        festivalId:Int,
        page:Int,
        size:Int,
    ):ResponseNearbyPlaceDto
}