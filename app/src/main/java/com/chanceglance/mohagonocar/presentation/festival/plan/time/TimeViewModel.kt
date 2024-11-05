package com.chanceglance.mohagonocar.presentation.festival.plan.time

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chanceglance.mohagonocar.BuildConfig
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import com.chanceglance.mohagonocar.domain.repository.KakaoRepository
import com.chanceglance.mohagonocar.extension.AddressState
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
class TimeViewModel @Inject constructor(
    private val authRepository: AuthRepository, private val kakaoRepository: KakaoRepository
):ViewModel() {
    private val _selectDepartAddress = MutableLiveData<Boolean>() //출발지와 목적지 저장했는지
    private val _selectArrivalAddress = MutableLiveData<Boolean>()
    private val _selectDepartTime = MutableLiveData<Boolean>() // 년, 월, 일 데이터를 저장했는지
    private val _selectArrivalTime = MutableLiveData<Boolean>()

    val selectDepartAddress: LiveData<Boolean> = _selectDepartAddress
    val selectArrivalAddress: LiveData<Boolean> = _selectArrivalAddress
    val selectDepartTime: LiveData<Boolean> = _selectDepartTime
    val selectArrivalTime: LiveData<Boolean> = _selectArrivalTime


    private val _addressState = MutableStateFlow<AddressState>(AddressState.Loading)
    val addressState: StateFlow<AddressState> = _addressState.asStateFlow()

    fun getAddress(address: String) {
        val api = "KakaoAK ${BuildConfig.KAKAO_REST_API}"
        Log.d("searchViewModel", "api key: ${api}")

        viewModelScope.launch {
            kakaoRepository.getAddress(api, address).onSuccess { response ->
                _addressState.value = AddressState.Success(response)
                Log.d("searchViewModel", "addressState 성공!")
            }.onFailure {
                _addressState.value = AddressState.Error("Error response failure: ${it.message}")
                if (it is HttpException) {
                    try {
                        val errorBody: ResponseBody? = it.response()?.errorBody()
                        val errorBodyString = errorBody?.string() ?: ""
                        httpError(errorBodyString)
                    } catch (e: Exception) {
                        // JSON 파싱 실패 시 로깅
                        Log.e("searchViewModel", "Error parsing error body", e)
                    }
                }
            }
        }
    }

    private fun httpError(errorBody: String) {
        // 전체 에러 바디를 로깅하여 디버깅
        Log.e("searchViewModel", "Full error body: $errorBody")

        // JSONObject를 사용하여 메시지 추출
        val jsonObject = JSONObject(errorBody)
        val errorMessage = jsonObject.optString("message", "Unknown error")

        // 추출된 에러 메시지 로깅
        Log.e("searchViewModel", "Error message: $errorMessage")
    }

    fun isSelectDepartAddress(decide:Boolean){
        _selectDepartAddress.value=decide
    }

    fun isSelectArrivalAddress(decide:Boolean){
        _selectArrivalAddress.value=decide
    }
    fun isSelectDepartTime(decide:Boolean){
        _selectDepartTime.value=decide
        Log.d("timeViewModel","depart is ${decide}")
    }
    fun isSelectArrivalTime(decide: Boolean){
        _selectArrivalTime.value=decide
        Log.d("timeViewModel","arrival is ${decide}")
    }


}