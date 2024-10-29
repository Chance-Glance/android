package com.chanceglance.mohagonocar.data.responseDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
        val subPaths:List<SubPaths>,
    ){
        @Serializable
        data class SubPaths(
            @SerialName("distance")
            val distance:Double,
            @SerialName("sectionTime")
            val sectionTime:Int,
            @SerialName("pathType")
            val pathType:String,
            @SerialName("busNo")
            val busNo:String,
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
        )
    }
}