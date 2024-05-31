package com.fikrilal.narate_mobile_apps._core.data.api

import com.fikrilal.narate_mobile_apps._core.data.model.auth.RegisterResponse
import com.fikrilal.narate_mobile_apps._core.data.model.auth.ResponseLogin
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoriesResponse
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoryDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {
    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name") name: String,
        @Field("email") userEmail: String,
        @Field("password") userPassword: String
    ):  Response<RegisterResponse>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("email") userEmail: String,
        @Field("password") userPassword: String
    ): Response<ResponseLogin>

    @POST("stories")
    @Multipart
    suspend fun addNewStory(
        @Part("description") description: String,
        @Part("photo") photo: String,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?,
        @Header("Authorization") authorization: String
    ): Response<StoryDetailResponse>

    @POST("stories/guest")
    @Multipart
    suspend fun addNewStoryGuest(
        @Part("description") description: String,
        @Part("photo") photo: String,
        @Part("lat") lat: Double?,
        @Part("lon") lon: Double?
    ): Response<StoryDetailResponse>

    @GET("stories")
    suspend fun getAllStories(
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("location") location: Int?,
        @Header("Authorization") authorization: String
    ): Response<StoriesResponse>

    @GET("stories/{id}")
    suspend fun getStoryDetail(
        @Path("id") storyId: String,
        @Header("Authorization") authorization: String
    ): Response<StoryDetailResponse>
}