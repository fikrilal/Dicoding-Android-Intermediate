package com.fikrilal.narate_mobile_apps._core.data.repository.story

import android.util.Log
import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoriesResponse
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoryDetailResponse
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.UserPreferences
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class StoryRepository @Inject constructor(
    private val apiServices: ApiServices,
    val userPreferences: UserPreferences
) {
    suspend fun addNewStory(
        description: String,
        photo: File,
        lat: Double?,
        lon: Double?,
        token: String
    ): StoryDetailResponse {
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
        val photoRequestBody = photo.asRequestBody("image/*".toMediaTypeOrNull())
        val photoPart = MultipartBody.Part.createFormData("photo", photo.name, photoRequestBody)

        val latRequestBody = lat?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())
        val lonRequestBody = lon?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())

        Log.d("StoryRepository", "Using token for addNewStory: $token")

        val response = apiServices.addNewStory(
            description = descriptionRequestBody,
            photo = photoPart,
            lat = latRequestBody,
            lon = lonRequestBody,
            authorization = token
        )

        Log.d("StoryRepository", "addNewStory response: ${response.isSuccessful}")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("StoryRepository", "Error adding new story: $errorBody")
            throw Exception("Error adding new story: $errorBody")
        }
    }


    suspend fun addNewStoryGuest(
        description: String,
        photo: String,
        lat: Double?,
        lon: Double?
    ): StoryDetailResponse {
        val response = apiServices.addNewStoryGuest(description, photo, lat, lon)
        Log.d("StoryRepository", "addNewStoryGuest response: ${response.isSuccessful}")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?
    ): StoriesResponse {
        val token = userPreferences.getUserToken()?.also {
            Log.d("StoryRepository", "Retrieved token: $it")
        } ?: throw Exception("Token is null or empty")

        Log.d("StoryRepository", "Using token for getAllStories: $token")
        val response = apiServices.getAllStories(page, size, location, "Bearer $token")
        Log.d("StoryRepository", "getAllStories response: ${response.isSuccessful}")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error fetching stories: ${response.message()}")
        }
    }

    suspend fun getStoryDetail(storyId: String): StoryDetailResponse {
        val token = userPreferences.getUserToken()?.also {
            Log.d("StoryRepository", "Retrieved token: $it")
        } ?: throw Exception("Token is null or empty")

        Log.d("StoryRepository", "Using token for getStoryDetail: $token")
        val response = apiServices.getStoryDetail(storyId, "Bearer $token")
        Log.d("StoryRepository", "getStoryDetail response: ${response.isSuccessful}")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception("Error fetching story detail: ${response.message()}")
        }
    }
}
