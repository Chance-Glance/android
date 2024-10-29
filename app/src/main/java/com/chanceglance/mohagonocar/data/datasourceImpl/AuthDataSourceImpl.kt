package com.chanceglance.mohagonocar.data.datasourceImpl

import com.chanceglance.mohagonocar.data.datasource.AuthDataSource
import com.chanceglance.mohagonocar.data.requestDto.RequestTravelCourseDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import com.chanceglance.mohagonocar.data.service.AuthService
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
):AuthDataSource {
    override suspend fun getFestival(page: Int, size: Int): ResponseFestivalDto =
        authService.getFesitvals(page, size)

    override suspend fun getNearbyPlace(
        festivalId: Int,
        page: Int,
        size: Int
    ): ResponseNearbyPlaceDto = authService.getNearbyPlaces(festivalId, page, size)

    override suspend fun getTravelCourse(requestTravelCourseDto: RequestTravelCourseDto): ResponseTravelCourseDto = authService.getTravelCourse(requestTravelCourseDto)
}