package com.chanceglance.mohagonocar.data.datasourceImpl

import com.chanceglance.mohagonocar.data.datasource.KakaoDataSource
import com.chanceglance.mohagonocar.data.responseDto.ResponseAddressDto
import com.chanceglance.mohagonocar.data.service.KakaoMapService
import javax.inject.Inject

class KakaoDataSourceImpl @Inject constructor(
    private val kakaoMapService: KakaoMapService
): KakaoDataSource {
    override suspend fun getAddress(token: String, address: String): ResponseAddressDto =kakaoMapService.getAddress(token,address)

}