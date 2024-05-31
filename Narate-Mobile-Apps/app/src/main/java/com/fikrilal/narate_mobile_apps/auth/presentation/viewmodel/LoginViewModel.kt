package com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<AuthResult<Any>>(AuthResult.Idle)
    val loginState: StateFlow<AuthResult<Any>> get() = _loginState

    fun login(email: String, password: String) {
        _loginState.value = AuthResult.Loading
        viewModelScope.launch {
            try {
                val result = authRepository.login(email, password)
                _loginState.value = AuthResult.Success(result)
            } catch (e: Exception) {
                _loginState.value = AuthResult.Error(e)
            }
        }
    }
}
