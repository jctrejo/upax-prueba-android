package com.android.upax_prueba_android.data.repository

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface RepositoryProfile {
    suspend fun getProfile(name: String, url: String): Flow<AppResource<ProfileModel>>
}