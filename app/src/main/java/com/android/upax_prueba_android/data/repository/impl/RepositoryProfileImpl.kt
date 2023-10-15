package com.android.upax_prueba_android.data.repository.impl

import com.android.upax_prueba_android.data.model.ProfileModel
import com.android.upax_prueba_android.data.source.remote.service.ServiceProfile
import com.android.upax_prueba_android.data.repository.RepositoryProfile
import com.android.upax_prueba_android.core.AppResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class RepositoryProfileImpl @Inject constructor(
    private val api: ServiceProfile
) : RepositoryProfile {

    override suspend fun getProfile(name: String, url: String): Flow<AppResource<ProfileModel>> = flow {
        try {
            emit(AppResource.Loading)
            val response = ProfileModel(1, name, url)
            emit(AppResource.Success(response))
        } catch (exception: HttpException) {
            emit(AppResource.Error(exception.code().toString()))
        } catch (exception: IOException) {
            emit(AppResource.Error(exception.toString()))
        }
    }
}