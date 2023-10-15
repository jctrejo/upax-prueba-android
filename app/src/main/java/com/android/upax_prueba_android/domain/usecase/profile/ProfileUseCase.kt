package com.android.upax_prueba_android.domain.usecase.profile

import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.ProfileModel
import kotlinx.coroutines.flow.Flow

interface ProfileUseCase {
    suspend operator fun invoke(name: String, url: String): Flow<AppResource<ProfileModel>>
}
