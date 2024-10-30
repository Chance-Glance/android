package com.chanceglance.mohagonocar.data.datasourceImpl

import android.util.Log
import com.chanceglance.mohagonocar.data.datasource.AuthDataSource
import com.chanceglance.mohagonocar.data.requestDto.RequestTravelCourseDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import com.chanceglance.mohagonocar.data.service.AuthService
import kotlinx.serialization.json.Json
import java.io.IOException
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authService: AuthService
):AuthDataSource {
    private val json = Json {
        ignoreUnknownKeys = true
        classDiscriminator = "pathType"
    }

    override suspend fun getFestival(page: Int, size: Int): ResponseFestivalDto =
        authService.getFesitvals(page, size)

    override suspend fun getNearbyPlace(
        festivalId: Int,
        page: Int,
        size: Int
    ): ResponseNearbyPlaceDto = authService.getNearbyPlaces(festivalId, page, size)

    override suspend fun getTravelCourse(requestTravelCourseDto: RequestTravelCourseDto): String {
        val response = authService.getTravelCourse(requestTravelCourseDto)

        // JSON 응답이 성공적으로 수신되었는지 확인하고 JSON 문자열로 변환
        val responseString = response.body()?.string()
            ?: throw IOException("Failed to get response body")

        // 원본 JSON 문자열을 로깅
        Log.d("authDataSource", "Full JSON response from server: $responseString")

        return responseString
    }

   /* override suspend fun getTravelCourse(requestTravelCourseDto: RequestTravelCourseDto): ResponseTravelCourseDto = authService.getTravelCourse(requestTravelCourseDto)*/
}