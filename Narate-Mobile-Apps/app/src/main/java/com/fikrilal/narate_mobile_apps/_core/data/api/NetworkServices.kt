package com.fikrilal.narate_mobile_apps._core.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkServices {
    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .build()
        chain.proceed(newRequest)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(com.fikrilal.narate_mobile_apps.BuildConfig.API_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiServices by lazy {
        retrofit.create(ApiServices::class.java)
    }
}