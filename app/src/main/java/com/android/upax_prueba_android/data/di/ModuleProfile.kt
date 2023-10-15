package com.android.upax_prueba_android.data.di

import com.android.upax_prueba_android.data.source.remote.NetworkClient
import com.android.upax_prueba_android.data.source.remote.service.ServiceProfile
import com.android.upax_prueba_android.data.repository.RepositoryProfile
import com.android.upax_prueba_android.data.repository.impl.RepositoryProfileImpl
import com.android.upax_prueba_android.domain.usecase.profile.ProfileUseCase
import com.android.upax_prueba_android.domain.usecase.profile.impl.ProfileUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ModuleProfile {

    @Provides
    fun getServicesProfile(): ServiceProfile {
        return NetworkClient().getServiceProfile()
    }

    @Provides
    fun getRepositoryProfile(service: ServiceProfile): RepositoryProfile {
        return RepositoryProfileImpl(service)
    }

    @Provides
    fun getUseCaseProfile(repository: RepositoryProfile): ProfileUseCase {
        return ProfileUseCaseImpl(repository)
    }
}
