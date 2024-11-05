package com.chanceglance.mohagonocar.di

import com.chanceglance.mohagonocar.data.datasource.AuthDataSource
import com.chanceglance.mohagonocar.data.datasource.KakaoDataSource
import com.chanceglance.mohagonocar.data.datasourceImpl.AuthDataSourceImpl
import com.chanceglance.mohagonocar.data.datasourceImpl.KakaoDataSourceImpl
import com.chanceglance.mohagonocar.data.repositoryImpl.AuthRepositoryImpl
import com.chanceglance.mohagonocar.data.repositoryImpl.KakaoRepositoryImpl
import com.chanceglance.mohagonocar.domain.repository.AuthRepository
import com.chanceglance.mohagonocar.domain.repository.KakaoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BindModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindKakaoRepository(kakaoRepositoryImpl: KakaoRepositoryImpl): KakaoRepository

    @Binds
    @Singleton
    abstract fun provideAuthDataSource(authDataSourceImpl: AuthDataSourceImpl): AuthDataSource

    @Binds
    @Singleton
    abstract fun provideKakaoDataSource(kakaoDataSourceImpl: KakaoDataSourceImpl): KakaoDataSource
}