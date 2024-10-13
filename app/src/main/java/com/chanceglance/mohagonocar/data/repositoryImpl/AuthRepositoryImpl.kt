package com.chanceglance.mohagonocar.data.repositoryImpl

import com.chanceglance.mohagonocar.data.datasource.AuthDataSource
import com.chanceglance.mohagonocar.data.responseDto.ResponseFestivalDto
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
):AuthRepository {
    override suspend fun getFestival(page: Int, size: Int): Result<ResponseFestivalDto> {
        return runCatching {
            authDataSource.getFestival(page, size)
        }
    }
}