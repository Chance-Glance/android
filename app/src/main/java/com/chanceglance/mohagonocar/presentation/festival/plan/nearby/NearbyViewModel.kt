package com.chanceglance.mohagonocar.presentation.festival.plan.nearby

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import com.chanceglance.mohagonocar.extension.FestivalState
import com.chanceglance.mohagonocar.extension.NearbyPlaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NearbyViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {
    private val _nearbyPLaceState = MutableStateFlow<NearbyPlaceState>(NearbyPlaceState.Loading)
    val nearbyPlaceState:StateFlow<NearbyPlaceState> = _nearbyPLaceState.asStateFlow()

    fun getNearbyPlace(festivalId:Int){
        viewModelScope.launch {
            authRepository.getNearbyPlace(festivalId,0,20).onSuccess { response->
                _nearbyPLaceState.value=NearbyPlaceState.Success(response)
                Log.d("nearbyViewModel","nearbyViewModel - nearby place 가져오기 성공!")
            }.onFailure {
                _nearbyPLaceState.value= NearbyPlaceState.Error("Error response failure: ${it.message}")
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
}