package com.chanceglance.mohagonocar.data.repositoryImpl

import android.util.Log
import com.chanceglance.mohagonocar.data.datasource.KakaoDataSource
import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto
import com.chanceglance.mohagonocar.domain.repository.KakaoRepository
import javax.inject.Inject

class KakaoRepositoryImpl @Inject constructor(
    private val kakaoDataSource: KakaoDataSource
): KakaoRepository {
    override suspend fun getAddress(token: String, address: String): Result<ResponseAddressDto> {
        return runCatching {
            kakaoDataSource.getAddress(token,address)
        }.onFailure {
            Log.e("kakaoRepositoryImpl", "kakaoRepositoryImpl 실패", it)
        }
    }


}