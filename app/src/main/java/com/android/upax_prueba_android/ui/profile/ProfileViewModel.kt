package com.android.upax_prueba_android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.upax_prueba_android.core.AppResource
import com.android.upax_prueba_android.core.AppUiState
import com.android.upax_prueba_android.data.model.ProfileModel
import com.android.upax_prueba_android.domain.usecase.profile.ProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase,
) : ViewModel() {
    private val _uiStateProfile = MutableLiveData<AppUiState<ProfileModel>>()
    val uiStateProfile: LiveData<AppUiState<ProfileModel>> get() = _uiStateProfile

    fun getProfile(name: String, url: String) = viewModelScope.launch {
        profileUseCase.invoke(name, url).collect { result ->
            when(result) {
                is AppResource.Loading -> {
                    _uiStateProfile.value = AppUiState.Loading()
                }

                is AppResource.Success -> {
                    _uiStateProfile.value = AppUiState.Success(result.item)
                }

                is AppResource.Error -> {
                    _uiStateProfile.value = AppUiState.Error(result.throwable)
                }
            }
        }
    }
}