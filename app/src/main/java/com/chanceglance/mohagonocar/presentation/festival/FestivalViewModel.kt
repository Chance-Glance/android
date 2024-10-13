package com.chanceglance.mohagonocar.presentation.festival

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import com.chanceglance.mohagonocar.extension.FestivalState
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
class FestivalViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModel() {
    private val _festivalState = MutableStateFlow<FestivalState>(FestivalState.Loading)
    val festivalState:StateFlow<FestivalState> = _festivalState.asStateFlow()

    fun getFestival(){
        viewModelScope.launch {
            authRepository.getFestival(0, 20).onSuccess {response->
                _festivalState.value = FestivalState.Success(response)
                Log.d("festivalViewModel","FestivalViewModel - festival 가져오기 성공!")
            }.onFailure {
                _festivalState.value=FestivalState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""

                        // 전체 에러 바디를 로깅하여 디버깅
                        Log.e("festivalViewModel", "Full error body: $errorBodyString")

                        // JSONObject를 사용하여 메시지 추출
                        val jsonObject = JSONObject(errorBodyString)
                        val errorMessage = jsonObject.optString("message", "Unknown error")

                        // 추출된 에러 메시지 로깅
                        Log.e("festivalViewModel", "Error message: $errorMessage")
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Log.e("festivalViewModel", "Error parsing error body", e)
                    }
                }
            }
        }
    }
}