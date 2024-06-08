package com.fikrilal.narate_mobile_apps.homepage.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoriesResponse
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.AuthRepository
import com.fikrilal.narate_mobile_apps._core.data.repository.auth.UserPreferences
import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
import com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel.HomeViewModel
import com.fikrilal.narate_mobile_apps.homepage.utils.DifferWrapper
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var apiServices: ApiServices
    private lateinit var userPreferences: UserPreferences
    private lateinit var authRepository: AuthRepository
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var differWrapper: DifferWrapper<Story>

    @Before
    fun setUp() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.isLoggable(any(), any()) } returns false
        Dispatchers.setMain(testDispatcher)
        apiServices = mockk(relaxed = true)
        userPreferences = mockk(relaxed = true)
        authRepository = mockk(relaxed = true)
        differWrapper = mockk(relaxed = true)

        coEvery { userPreferences.userToken } returns flowOf("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUllVzdjRldFUktuS0JVWVIiLCJpYXQiOjE3MTc1NTUwMTB9.4ccR_v-mgPAB3yKM4akhm-eVCv-xCbiZ59_atM1nOjM")
        coEvery {
            apiServices.getAllStories(
                any(),
                any(),
                any(),
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUllVzdjRldFUktuS0JVWVIiLCJpYXQiOjE3MTc1NTUwMTB9.4ccR_v-mgPAB3yKM4akhm-eVCv-xCbiZ59_atM1nOjM"
            )
        } returns Response.success(
            StoriesResponse(
                false,
                "Success",
                listOf(
                    Story(
                        "2022-01-01T00:00:00Z",
                        "Lorem Ipsum",
                        "story-FvU4u0Vp2S3PMsFg",
                        -10.212,
                        -16.002,
                        "Dimas",
                        "https://story-api.dicoding.dev/images/stories/photos-1641623658595_dummy-pic.png"
                    )
                )
            )
        )

        val storyRepository = StoryRepository(apiServices, userPreferences)
        viewModel = HomeViewModel(
            storyRepository,
            authRepository,
            apiServices,
            userPreferences,
            differWrapper
        )
    }

    @Test
    fun allStoriesSuccessfullyLoaded() = runTest(testDispatcher) {
        val expectedStories = listOf(
            Story("2022-01-01T00:00:00Z", "Lorem Ipsum", "story-FvU4u0Vp2S3PMsFg", -10.212, -16.002, "Dimas", "https://story-url")
        )
        val pagingData = PagingData.from(expectedStories)

        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.success(
            StoriesResponse(false, "Success", expectedStories)
        )

        coEvery { differWrapper.submitData(any()) } just Runs

        val collectJob = launch {
            viewModel.allStories.collect { receivedPagingData ->
                val differ = AsyncPagingDataDiffer(
                    diffCallback = object : androidx.recyclerview.widget.DiffUtil.ItemCallback<Story>() {
                        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem.id == newItem.id
                        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean = oldItem == newItem
                    },
                    updateCallback = noopListUpdateCallback,
                    workerDispatcher = testDispatcher
                )
                differ.submitData(receivedPagingData)
                assertNotNull("Received data should not be null", receivedPagingData)
                advanceUntilIdle()
                assertEquals("Expected number of items did not match", expectedStories.size, differ.snapshot().size)
                assertEquals("Expected first item's name did not match", "Dimas", differ.snapshot().items[0].name)
            }
        }
        advanceUntilIdle()
        collectJob.cancel()
    }

    @After
    fun tearDown() {
        viewModel.viewModelScope.cancel()
        Dispatchers.resetMain()
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }
}