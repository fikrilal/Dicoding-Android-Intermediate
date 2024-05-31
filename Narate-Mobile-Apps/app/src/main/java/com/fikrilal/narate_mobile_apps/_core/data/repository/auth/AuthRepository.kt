package com.fikrilal.narate_mobile_apps._core.data.repository.auth

import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.auth.RegisterResponse
import com.fikrilal.narate_mobile_apps._core.data.model.auth.ResponseLogin

class AuthRepository(
    private val apiServices: ApiServices,
    private val userPreferences: UserPreferences
) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val response = apiServices.register(name, email, password)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Registration failed: ${response.errorBody()?.string()}")
        }
    }

    suspend fun login(email: String, password: String): ResponseLogin {
        val response = apiServices.login(email, password)
        if (response.isSuccessful) {
            response.body()?.loginResult?.let {
                if (it.token != null && it.userId != null && it.name != null) {
                    userPreferences.saveUserDetails(it.token!!, it.userId!!, it.name!!)
                } else {
                    throw Exception("Login failed: Essential user details are missing")
                }
            } ?: throw Exception("Login failed: No login result available")
            return response.body() ?: throw Exception("Login failed, body is null")
        } else {
            throw Exception("Login failed: ${response.errorBody()?.string()}")
        }
    }
}
