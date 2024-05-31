package com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel

sealed class AuthResult<out T> {
    object Idle : AuthResult<Nothing>()
    object Loading : AuthResult<Nothing>()
    data class Success<out T>(val data: T) : AuthResult<T>()
    data class Error(val exception: Throwable) : AuthResult<Nothing>()
}
