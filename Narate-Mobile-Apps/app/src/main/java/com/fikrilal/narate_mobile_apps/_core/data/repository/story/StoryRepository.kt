package com.fikrilal.narate_mobile_apps._core.data.repository.story

import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoriesResponse
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoryDetailResponse
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.UserPreferences

class StoryRepository(
    private val apiServices: ApiServices,
    private val userPreferences: UserPreferences
) {
    suspend fun addNewStory(
        description: String,
        photo: String,
        lat: Double?,
        lon: Double?,
        token: String
    ): StoryDetailResponse {
        val response = apiServices.addNewStory(description, photo, lat, lon, "Bearer $token")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun addNewStoryGuest(
        description: String,
        photo: String,
        lat: Double?,
        lon: Double?
    ): StoryDetailResponse {
        val response = apiServices.addNewStoryGuest(description, photo, lat, lon)
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun getAllStories(
        page: Int?,
        size: Int?,
        location: Int?,
        token: String
    ): StoriesResponse {
        val response = apiServices.getAllStories(page, size, location, "Bearer $token")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }

    suspend fun getStoryDetail(storyId: String, token: String): StoryDetailResponse {
        val response = apiServices.getStoryDetail(storyId, "Bearer $token")
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            throw Exception(response.message())
        }
    }
}