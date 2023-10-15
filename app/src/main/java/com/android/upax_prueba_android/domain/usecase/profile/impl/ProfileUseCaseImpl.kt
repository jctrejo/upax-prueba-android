package com.android.upax_prueba_android.domain.usecase.profile.impl

import com.android.upax_prueba_android.domain.usecase.profile.ProfileUseCase
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.data.model.ProfileModel
import com.android.upax_prueba_android.data.repository.RepositoryProfile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileUseCaseImpl @Inject constructor(private val repository: RepositoryProfile) :
    ProfileUseCase {

    override suspend operator fun invoke(
        name: String,
        url: String,
    ): Flow<AppResource<ProfileModel>> =
        repository.getProfile(name, url)
}
