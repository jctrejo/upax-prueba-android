package com.android.upax_prueba_android.core

sealed class AppResource<out T> {
    object Loading : AppResource<Nothing>()
    data class Success<T>(val item: T) : AppResource<T>()
    data class Error(val throwable: String) : AppResource<Nothing>()
}
