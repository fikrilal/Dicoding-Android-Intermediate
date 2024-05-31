package com.fikrilal.narate_mobile_apps.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val userPreferences: UserPreferences
) : ViewModel() {

    fun isUserLoggedIn(): Boolean {
        return runBlocking {
            val token = userPreferences.getUserToken()?.first()
            token != null
        }
    }
}
