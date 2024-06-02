package com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.AuthRepository
import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> get() = _stories

    init {
        fetchStories()
    }

    private fun fetchStories() {
        viewModelScope.launch {
            try {
                val response = storyRepository.getAllStories(null, null, null)
                _stories.value = response.listStory
//                Log.d("HomeViewModel", "Fetched stories: ${response.listStory}")
            } catch (e: Exception) {
//                Log.e("HomeViewModel", "Error fetching stories", e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
//                Log.d("HomeViewModel", "Logged out successfully")
            } catch (e: Exception) {
//                Log.e("HomeViewModel", "Error logging out", e)
            }
        }
    }
}
