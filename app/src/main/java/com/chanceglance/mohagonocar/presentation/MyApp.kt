package com.chanceglance.mohagonocar.presentation

import android.app.Application
import com.chanceglance.mohagonocar.BuildConfig
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoMapSdk.init(this, BuildConfig.NATIVE_APP_KEY);
    }
}