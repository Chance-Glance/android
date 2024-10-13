package com.chanceglance.mohagonocar.domain.repository

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto

interface AuthRepository {
    suspend fun getFestival(
        page:Int,
        size:Int,
    ):Result<ResponseFestivalDto>
}