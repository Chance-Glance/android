package com.chanceglance.mohagonocar.di

import android.content.SharedPreferences
import com.chanceglance.mohagonocar.data.service.AuthService
import com.chanceglance.mohagonocar.data.service.KakaoMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun provideService(
        @BaseUrlRetrofit retrofit: Retrofit
    ): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideKakaoMapService(
        @KakaoMapRetrofit retrofit: Retrofit
    ): KakaoMapService = retrofit.create(KakaoMapService::class.java)

    @Provides
    @Singleton
    fun provideHeaderIntercepter(autoLoginPrefeneces: SharedPreferences): HeaderInterceptor =
        HeaderInterceptor(autoLoginPrefeneces)

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }
}