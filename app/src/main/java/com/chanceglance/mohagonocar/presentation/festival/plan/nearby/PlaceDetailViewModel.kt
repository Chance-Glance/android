package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto

class PlaceDetailViewModel:ViewModel() {
    private var place: ResponseNearbyPlaceDto.Data.Item? =null

    fun setPlace(newPlace:ResponseNearbyPlaceDto.Data.Item){
        place = newPlace
    }

    fun getPlace() : ResponseNearbyPlaceDto.Data.Item = place!!
}