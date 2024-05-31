package com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.AuthRepository
import com.fikrilal.narate_mobile_apps._core.data.model.auth.RegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableLiveData<AuthResult<RegisterResponse>>()
    val registerState: LiveData<AuthResult<RegisterResponse>> = _registerState
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registerState.value = AuthResult.Loading
            try {
                val response = authRepository.register(name, email, password)
                _registerState.value = AuthResult.Success(response)
            } catch (e: Exception) {
                _registerState.value = AuthResult.Error(e)
            }
        }
    }
}

sealed class Result<out T> {
    object Loading : AuthResult<Nothing>()
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Exception) : AuthResult<Nothing>()
}