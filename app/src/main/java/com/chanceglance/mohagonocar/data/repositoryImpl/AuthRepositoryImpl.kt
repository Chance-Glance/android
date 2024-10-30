package com.chanceglance.mohagonocar.data.repositoryImpl

import android.util.Log
import com.chanceglance.mohagonocar.data.datasource.AuthDataSource
import com.chanceglance.mohagonocar.data.requestDto.RequestTravelCourseDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {
    override suspend fun getFestival(page: Int, size: Int): Result<ResponseFestivalDto> {
        return runCatching {
            authDataSource.getFestival(page, size)
        }
    }

    override suspend fun getNearbyPlace(
        festivalId: Int,
        page: Int,
        size: Int
    ): Result<ResponseNearbyPlaceDto> {
        return runCatching {
            authDataSource.getNearbyPlace(festivalId, page, size)
        }
    }

    override suspend fun getTravelCourse(
        festivalId: Int,
        travelDate: LocalDate,
        leaveTime: LocalTime,
        arrivalTime: LocalTime,
        travelPlaceIds: List<Int>
    ): Result<ResponseTravelCourseDto> {
        return runCatching {
            val responseString = authDataSource.getTravelCourse(
                RequestTravelCourseDto(festivalId, travelDate, leaveTime, arrivalTime, travelPlaceIds)
            )
            Log.d("authRepositoryImpl", "Full JSON response string: $responseString")

            val parsedResponse = parseResponse(responseString)

            if (parsedResponse == null) {
                Log.e("authRepositoryImpl", "Failed to parse JSON response: $responseString")
                throw SerializationException("Failed to parse JSON response")
            }

            parsedResponse // 파싱 성공 시 결과 반환
        }
    }


    /* override suspend fun getTravelCourse(
         festivalId: Int,
         travelDate: LocalDate,
         leaveTime: LocalTime,
         arrivalTime: LocalTime,
         travelPlaceIds: List<Int>
     ): Result<ResponseTravelCourseDto> {
         return runCatching {
             authDataSource.getTravelCourse(RequestTravelCourseDto(festivalId, travelDate, leaveTime, arrivalTime, travelPlaceIds))
         }
     }*/

    // JSON 파서 객체 생성
    private val json = Json {
        //ignoreUnknownKeys = true
        classDiscriminator = "pathType"  // JSON의 "pathType"을 기준으로 분기
    }

    // 서버 응답을 파싱하는 함수
    fun parseResponse(response: String): ResponseTravelCourseDto? {
        return try {
            json.decodeFromString(ResponseTravelCourseDto.serializer(), response)
        } catch (e: SerializationException) {
            Log.e("authRepositoryImpl", "JSON parsing error: ${e.message}")
            null  // 기본값이나 에러 처리
        }
    }
}