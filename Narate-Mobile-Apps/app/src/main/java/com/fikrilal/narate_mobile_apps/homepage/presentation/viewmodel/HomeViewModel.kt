package com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.AuthRepository
import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.UserPreferences
import com.fikrilal.narate_mobile_apps.homepage.data.sources.StoryPagingSource
import com.fikrilal.narate_mobile_apps.homepage.utils.DifferWrapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository,
    private val apiServices: ApiServices,
    val userPreferences: UserPreferences,
    private val differWrapper: DifferWrapper<Story>
) : ViewModel() {

    private val _pagingData = MutableSharedFlow<PagingData<Story>>(replay = 1)
    val pagingData: Flow<PagingData<Story>> = _pagingData.asSharedFlow()

    init {
        viewModelScope.launch {
            userPreferences.userToken.filterNotNull().collect { token ->
                val pager = Pager(
                    PagingConfig(pageSize = 20)
                ) {
                    StoryPagingSource(apiServices, token, 0)
                }.flow.cachedIn(viewModelScope)

                pager.collect {
                    differWrapper.submitData(it)  // Submit data through the DifferWrapper
                    _pagingData.emit(it)  // Optionally continue to expose it through a Flow if needed elsewhere
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val allStories: Flow<PagingData<Story>> = userPreferences.userToken
        .filterNotNull()
        .flatMapLatest { token ->
            Pager(
                PagingConfig(pageSize = 20)
            ) {
                StoryPagingSource(apiServices, token, 0)
            }.flow
        }.cachedIn(viewModelScope)


    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> get() = _stories

    private val _storiesWithLocation = MutableStateFlow<List<Story>>(emptyList())
    val storiesWithLocation: StateFlow<List<Story>> = _storiesWithLocation

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


//    init {
//        fetchStoriesWithLocation()
//    }
//
//    private fun fetchStoriesWithLocation() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            _error.value = null
//            try {
//                val response = storyRepository.getAllStories(null, null, 1)
//                if (!response.error) {
//                    _storiesWithLocation.value = response.listStory
//                } else {
//                    _error.value = response.message
//                }
//            } catch (e: Exception) {
//                _error.value = e.message
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
            } catch (e: Exception) {
                //
            }
        }
    }
}
