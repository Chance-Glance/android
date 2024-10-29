package com.chanceglance.mohagonocar.data.responseDto

import com.chanceglance.mohagonocar.data.converter.LocalDateSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class ResponseFestivalDto (
    @SerialName("isSuccess")
    val isSuccess : Boolean,
    @SerialName("code")
    val code:String,
    @SerialName("message")
    val message:String,
    @SerialName("data")
    val data:Data
){
    @Serializable
    data class Data(
        @SerialName("currentPage")
        val currentPage:Int,
        @SerialName("hasPrevious")
        val hasPrevious:Boolean,
        @SerialName("hasNext")
        val hasNext:Boolean,
        @SerialName("totalPages")
        val totalPages:Int,
        @SerialName("totalItems")
        val totalItems:Int,
        @SerialName("items")
        val items:List<Item>
    ){
        @Serializable
        data class Item(
            @SerialName("id")
            val id:Int,
            @SerialName("name")
            val name:String,
            @SerialName("activePeriod")
            val activePeriod: ActivePeriod,
            @SerialName("description")
            val description:String,
            @SerialName("address")
            val address:String,
            @SerialName("location")
            val location:Location,
            @SerialName("imageUrlList")
            val imageUrlList:List<String>,
        ){
            @Serializable
            data class ActivePeriod(
                @Serializable (with = LocalDateSerializer::class)
                @SerialName("startDate")
                val startDate:LocalDate,
                @Serializable(with = LocalDateSerializer::class)
                @SerialName("endDate")
                val endDate:LocalDate
            )

            @Serializable
            data class Location(
                @SerialName("longitude")
                val longitude:Double,
                @SerialName("latitude")
                val latitude:Double,
            )
        }
    }
}

