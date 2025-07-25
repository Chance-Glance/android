package com.chanceglance.mohagonocar.data.service

import com.chanceglance.mohagonocar.data.requestDto.RequestTravelCourseDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.data.responseDto.ResponseTravelCourseDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("/api/v1/travel-plan")
    suspend fun getTravelCourse(
        @Body requestTravelCourseDto: RequestTravelCourseDto
    ): Response<ResponseBody>
}