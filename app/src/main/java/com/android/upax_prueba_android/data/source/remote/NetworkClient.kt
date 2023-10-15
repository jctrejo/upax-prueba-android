package com.android.upax_prueba_android.data.source.remote

import com.android.upax_prueba_android.core.Constants.URL_BASE
import com.android.upax_prueba_android.data.source.remote.service.Service
import com.android.upax_prueba_android.data.source.remote.service.ServiceProfile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkClient {
    private val api = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideOkHttpClient())

    fun getService(): Service {
        return api
            .build()
            .create(Service::class.java)
    }

    fun getServiceProfile(): ServiceProfile {
        return api
            .build()
            .create(ServiceProfile::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }
}