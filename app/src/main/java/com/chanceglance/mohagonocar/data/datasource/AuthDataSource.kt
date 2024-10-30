package com.chanceglance.mohagonocar.data.datasource

import com.chanceglance.mohagonocar.data.requestDto.RequestTravelCourseDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import okhttp3.ResponseBody
import retrofit2.Response

interface AuthDataSource {
    suspend fun getFestival(
        page: Int,
        size: Int,
    ): ResponseFestivalDto

    suspend fun getNearbyPlace(
        festivalId: Int,
        page: Int,
        size: Int,
    ): ResponseNearbyPlaceDto

    suspend fun getTravelCourse(
        requestTravelCourseDto: RequestTravelCourseDto
    ): String
}