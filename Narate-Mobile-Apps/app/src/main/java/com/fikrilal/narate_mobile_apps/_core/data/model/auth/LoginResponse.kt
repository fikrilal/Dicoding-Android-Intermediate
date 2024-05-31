package com.fikrilal.narate_mobile_apps._core.data.model.auth
import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("error") var error: Boolean? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("loginResult") var loginResult: LoginResult? = LoginResult()
)

data class LoginResult(
    @SerializedName("userId") var userId: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("token") var token: String? = null
)