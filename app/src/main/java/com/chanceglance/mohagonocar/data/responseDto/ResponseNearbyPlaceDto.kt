package com.chanceglance.mohagonocar.data.responseDto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseNearbyPlaceDto(
    @SerialName("isSuccess")
    val isSuccess: Boolean,
    @SerialName("code")
    val code:String,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:Data
    ) {
    @Serializable
    data class Data(
        @SerialName("currentPage")
        val currentPage: Int,
        @SerialName("hasPrevious")
        val hasPrevious: Boolean,
        @SerialName("hasNext")
        val hasNext: Boolean,
        @SerialName("totalPages")
        val totalPages: Int,
        @SerialName("totalItems")
        val totalItems: Int,
        @SerialName("items")
        val items: List<Item>,
    ) {
        @Serializable
        data class Item(
            @SerialName("id")
            val id: Int,
            @SerialName("name")
            val name: String,
            @SerialName("festivalId")
            val festivalId: Int,
            @SerialName("operatingSchedule")
            val operatingSchedule: List<String>,
            @SerialName("location")
            val location: ResponseFestivalDto.Data.Item.Location,
            @SerialName("address")
            val address: String,
            @SerialName("description")
            val description: String,
            @SerialName("placeType")
            val placeType:String,
            @SerialName("googlePlaceId")
            val googlePlaceId: String,
            @SerialName("imageUrlList")
            val imageUrlList: List<String>,
        )
    }
}