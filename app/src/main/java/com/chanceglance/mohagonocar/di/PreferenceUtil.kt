package com.chanceglance.mohagonocar.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    fun getString(key: String, defValue: String): String {
        Log.d("preferenceuti","getString: $key = $defValue")
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        Log.d("preferenceuti","setString: $key = $str")
        prefs.edit().putString(key, str).apply()
    }
}