package com.fikrilal.narate_mobile_apps.story.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoryDetailResponse
import kotlinx.coroutines.launch
import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoryDetailsViewModel @Inject constructor(
    private val storyRepository: StoryRepository
) : ViewModel() {

    private val _storyDetail = MutableLiveData<Story?>()
    val storyDetail: LiveData<Story?> = _storyDetail

    fun fetchStoryDetail(storyId: String) {
        viewModelScope.launch {
            try {
                val story = storyRepository.getStoryDetail(storyId)
                _storyDetail.postValue(story)
            } catch (e: Exception) {
                _storyDetail.postValue(null)
                Log.e("StoryDetailsViewModel", "Failed to fetch story details", e)
            }
        }
    }
}



