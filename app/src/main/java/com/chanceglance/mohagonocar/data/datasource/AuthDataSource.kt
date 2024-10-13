package com.chanceglance.mohagonocar.data.datasource

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto

interface AuthDataSource {
    suspend fun getFestival(
        page:Int,
        size:Int,
    ):ResponseFestivalDto
}