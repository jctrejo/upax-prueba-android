package com.android.upax_prueba_android.data.source.local.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext

class PreferencesImpl(private val prefs: SharedPreferences) : Preferences {

    override fun put(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    override fun put(key: String, value: Int) {
        prefs.edit().putInt(key, value).apply()
    }

    override fun put(key: String, value: Long) {
        prefs.edit().putLong(key, value).apply()
    }

    override fun put(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    override fun getString(key: String, value: String) = prefs.getString(key, value) ?: ""

    override fun getInt(key: String, value: Int) = prefs.getInt(key, value)

    override fun getLong(key: String, value: Long) = prefs.getLong(key, value)

    override fun getBoolean(key: String, value: Boolean) = prefs.getBoolean(key, value)

    override fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    companion object {

        @Volatile
        private var INSTANCE: Preferences? = null

        fun getInstance(@ApplicationContext context: Context, filename: String): Preferences {
            return INSTANCE ?: synchronized(this) {
                val instance = PreferencesImpl(
                    context.getSharedPreferences(filename, Context.MODE_PRIVATE)
                )
                INSTANCE = instance
                instance
            }
        }
    }
}
