package com.common.presistance

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences


typealias CommitResultCallback = ((Boolean) -> Unit)

class BasePreference private constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun getString(key: String, defValue: String): String {
        return sharedPreferences.getString(key, defValue) ?: defValue
    }

    fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    @SuppressLint("CommitPrefEdits")
    fun setString(key: String, value: String, callback: CommitResultCallback? = null) {
        sharedPreferences.edit().putString(key, value).execute(callback)
    }

    @SuppressLint("CommitPrefEdits")
    fun setInt(key: String, value: Int, callback: CommitResultCallback? = null) {
        sharedPreferences.edit().putInt(key, value).execute(callback)
    }

    @SuppressLint("CommitPrefEdits")
    fun setBoolean(key: String, value: Boolean, callback: CommitResultCallback? = null) {
        sharedPreferences.edit().putBoolean(key, value).execute(callback)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    @SuppressLint("CommitPrefEdits")
    fun setLong(key: String, value: Long, callback: CommitResultCallback? = null) {
        sharedPreferences.edit().putLong(key, value).execute(callback)
    }

    fun getLong(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    @SuppressLint("CommitPrefEdits")
    fun remove(key: String, callback: CommitResultCallback? = null) {
        sharedPreferences.edit().remove(key).execute(callback)
    }

    @SuppressLint("CommitPrefEdits")
    fun clear(callback: CommitResultCallback? = null) {
        sharedPreferences.edit().clear().execute(callback)
    }

    fun hasKey(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    @SuppressLint("CommitPrefEdits")
    fun setStringSet(key: String, value: Set<String>, callback: CommitResultCallback? = null) {
        sharedPreferences.edit().putStringSet(key, value).execute(callback)
    }

    fun getStringSet(key: String): Set<String> {
        return sharedPreferences.getStringSet(key, setOf()) ?: setOf()
    }

    private fun SharedPreferences.Editor.execute(callback: CommitResultCallback? = null) {
        if (null != callback) callback.invoke(this.commit()) else this.apply()
    }

    enum class BasePrefKey(val value: String) {
        INTROSCREEN("INTROSCREEN")
    }

    companion object {
        @JvmStatic
        fun getInstance(context: Context, prefName: String = "app_preferences"): BasePreference {
            return BasePreference(
                context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
            )
        }
    }

}