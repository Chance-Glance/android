package com.chanceglance.mohagonocar.domain.repository

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto

interface AuthRepository {
    suspend fun getFestival(
        page: Int,
        size: Int,
    ): Result<ResponseFestivalDto>

    suspend fun getNearbyPlace(
        festivalId: Int,
        page: Int,
        size: Int,
    ): Result<ResponseNearbyPlaceDto>
}