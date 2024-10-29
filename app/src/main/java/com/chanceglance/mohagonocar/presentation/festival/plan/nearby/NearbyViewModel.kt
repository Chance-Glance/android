package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanceglance.mohagonocar.data.responseDto.ResponseNearbyPlaceDto
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import com.chanceglance.mohagonocar.extension.FestivalState
import com.chanceglance.mohagonocar.extension.NearbyPlaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import okhttp3.internal.notify
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val countPlace: Int = 4

    private val _nearbyPLaceState = MutableStateFlow<NearbyPlaceState>(NearbyPlaceState.Loading)
    val nearbyPlaceState: StateFlow<NearbyPlaceState> = _nearbyPLaceState.asStateFlow()

    private val _selectPlaceList = MutableLiveData<List<ResponseNearbyPlaceDto.Data.Item>>()
    val selectPlaceList: LiveData<List<ResponseNearbyPlaceDto.Data.Item>> = _selectPlaceList

    private val _selectPlace = MutableLiveData<ResponseNearbyPlaceDto.Data.Item>()
    val selectPlace: LiveData<ResponseNearbyPlaceDto.Data.Item> = _selectPlace

    fun getNearbyPlace(festivalId: Int) {
        viewModelScope.launch {
            authRepository.getNearbyPlace(festivalId, 0, 20).onSuccess { response ->
                _nearbyPLaceState.value = NearbyPlaceState.Success(response)
                Log.d("nearbyViewModel", "nearbyViewModel - nearby place 가져오기 성공!")
            }.onFailure {
                _nearbyPLaceState.value =
                    NearbyPlaceState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""

                        // 전체 에러 바디를 로깅하여 디버깅
                        Log.e("nearbyViewModel", "Full error body: $errorBodyString")

                        // JSONObject를 사용하여 메시지 추출
                        val jsonObject = JSONObject(errorBodyString)
                        val errorMessage = jsonObject.optString("message", "Unknown error")

                        // 추출된 에러 메시지 로깅
                        Log.e("nearbyViewModel", "Error message: $errorMessage")
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Log.e("nearbyViewModel", "Error parsing error body", e)
                    }
                }
            }
        }
    }

    fun selectNearbyPlace(place: ResponseNearbyPlaceDto.Data.Item): Int {
        // 기존 리스트가 null일 경우 빈 리스트를 생성
        val currentList = _selectPlaceList.value?.toMutableList() ?: mutableListOf()

        if (currentList.contains(place))
            currentList.remove(place)
        else // 새로운 place를 리스트에 추가
            currentList.add(place)

        // 업데이트된 리스트를 MutableLiveData에 설정
        _selectPlaceList.value = currentList

        Log.d("nearbyViewModel", "select list - ${currentList}")

        return when {
            currentList.size in 1..countPlace -> 1
            currentList.size == 0 -> 0
            else -> -1
        }
    }

    fun isSelect(place: ResponseNearbyPlaceDto.Data.Item): Boolean {
        return _selectPlaceList.value?.contains(place) == true
    }
}