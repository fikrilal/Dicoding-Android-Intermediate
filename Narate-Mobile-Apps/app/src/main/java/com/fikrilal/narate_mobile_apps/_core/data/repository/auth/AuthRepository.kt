package com.fikrilal.narate_mobile_apps._core.data.repository.auth

import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.auth.RegisterResponse
import com.fikrilal.narate_mobile_apps._core.data.model.auth.ResponseLogin
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiServices: ApiServices,
    private val userPreferences: UserPreferences
) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val response = apiServices.register(name, email, password)
        if (response.isSuccessful) {
            return response.body() ?: throw Exception("Registration failed: Empty response body")
        } else {
            throw Exception("Registration failed: ${response.errorBody()?.string()}")
        }
    }

    suspend fun login(email: String, password: String): ResponseLogin {
        val response = apiServices.login(email, password)
        if (response.isSuccessful) {
            val loginResult = response.body()?.loginResult ?: throw Exception("Login failed: No login result available")
            val token = loginResult.token ?: throw Exception("Login failed: Missing token")
            val userId = loginResult.userId ?: throw Exception("Login failed: Missing userId")
            val name = loginResult.name ?: throw Exception("Login failed: Missing name")

            userPreferences.saveUserDetails(token, userId, name)
            return response.body() ?: throw Exception("Login failed, body is null")
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
    }
}