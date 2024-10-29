package com.chanceglance.mohagonocar.data.requestDto

import com.chanceglance.mohagonocar.data.converter.LocalDateSerializer
import com.chanceglance.mohagonocar.data.converter.LocalTimeSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.LocalTime

@Serializable
data class RequestTravelCourseDto (
    @SerialName("festivalId")
    val festivalId:Int,
    @Serializable(with = LocalDateSerializer::class)
    @SerialName("travelDate")
    val travelDate:LocalDate,
    @Serializable(with = LocalTimeSerializer::class)
    @SerialName("leaveTime")
    val leaveTime:LocalTime,
    @Serializable(with = LocalTimeSerializer::class)
    @SerialName("arrivalTime")
    val arrivalTime:LocalTime,
    @SerialName("travelPlaceIds")
    val travelPlaceIds:List<Int>
)