package com.chanceglance.mohagonocar.domain.repository

import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import java.time.LocalDate
import java.time.LocalTime

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

    suspend fun getTravelCourse(
        festivalId: Int,
        travelDate:LocalDate,
        leaveTime:LocalTime,
        arrivalTime:LocalTime,
        travelPlaceIds:List<Int>
    ):Result<ResponseTravelCourseDto>
}