package com.chanceglance.mohagonocar.data.responseDto

import android.util.Log
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class ResponseTravelCourseDto (
    @SerialName("isSuccess")
    val isSuccess:Boolean,
    @SerialName("code")
    val code:String,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:List<Data>
){
    @Serializable
    data class Data(
        @SerialName("startPlaceName")
        val startPlaceName:String,
        @SerialName("startLongitude")
        val startLongitude:Double,
        @SerialName("startLatitude")
        val startLatitude:Double,
        @SerialName("endPlaceName")
        val endPlaceName:String,
        @SerialName("endLongitude")
        val endLongitude:Double,
        @SerialName("endLatitude")
        val endLatitude:Double,
        @SerialName("totalTime")
        val totalTime:Int,
        @SerialName("totalDistance")
        val totalDistance:Double,
        @SerialName("subPaths")
        val subPaths:List<SubPath>,
    ){
        @Serializable(with = SubPathSerializer::class)
        sealed class SubPath {
            // `WALK` 타입일 때
            @Serializable
            @SerialName("WALK")
            data class Walk(
                @SerialName("distance")
                val distance: Double,
                @SerialName("sectionTime")
                val sectionTime: Int,
                @SerialName("pathType")
                val pathType: String = "WALK"
            ) : SubPath()

            // `BUS` 타입일 때
            @Serializable
            @SerialName("BUS")
            data class Bus(
                @SerialName("distance")
                val distance: Double,
                @SerialName("sectionTime")
                val sectionTime: Int,
                @SerialName("pathType")
                val pathType: String = "BUS",
                @SerialName("busNo")
                val busNo: String,
                @SerialName("busType")
                val busType:Int,
                @SerialName("startPlaceName")
                val startPlaceName: String,
                @SerialName("startLongitude")
                val startLongitude: Double,
                @SerialName("startLatitude")
                val startLatitude: Double,
                @SerialName("endPlaceName")
                val endPlaceName: String,
                @SerialName("endLongitude")
                val endLongitude: Double,
                @SerialName("endLatitude")
                val endLatitude: Double
            ) : SubPath()

            // `SUBWAY` 타입일 때
            @Serializable
            @SerialName("SUBWAY")
            data class Subway(
                @SerialName("distance")
                val distance: Double,
                @SerialName("sectionTime")
                val sectionTime: Int,
                @SerialName("pathType")
                val pathType: String = "SUBWAY",
                @SerialName("subwayLineName")
                val subwayLineName: String,
                @SerialName("startPlaceName")
                val startPlaceName: String,
                @SerialName("startLongitude")
                val startLongitude: Double,
                @SerialName("startLatitude")
                val startLatitude: Double,
                @SerialName("endPlaceName")
                val endPlaceName: String,
                @SerialName("endLongitude")
                val endLongitude: Double,
                @SerialName("endLatitude")
                val endLatitude: Double
            ) : SubPath()
        }
    }
}

object SubPathSerializer : JsonContentPolymorphicSerializer<ResponseTravelCourseDto.Data.SubPath>(
    ResponseTravelCourseDto.Data.SubPath::class
) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out ResponseTravelCourseDto.Data.SubPath> {
        val pathType = element.jsonObject["pathType"]?.jsonPrimitive?.contentOrNull

        Log.d("SubPathSerializer", "Deserializing pathType: $pathType")  // pathType 확인 로깅

        return when (pathType) {
            "WALK" -> ResponseTravelCourseDto.Data.SubPath.Walk.serializer()
            "BUS" -> ResponseTravelCourseDto.Data.SubPath.Bus.serializer()
            "SUBWAY" -> ResponseTravelCourseDto.Data.SubPath.Subway.serializer()
            else -> {
                Log.e("SubPathSerializer", "Unknown pathType: $pathType. Defaulting to Walk.")
                ResponseTravelCourseDto.Data.SubPath.Walk.serializer()  // 기본값으로 처리
            }
        }
    }
}

