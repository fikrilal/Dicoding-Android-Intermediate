package com.fikrilal.narate_mobile_apps.homepage.viewmodel

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
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
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.fail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.withTimeout
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
    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var differWrapper: DifferWrapper<Story>
    private lateinit var savedStateHandle: SavedStateHandle

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

        savedStateHandle = mockk(relaxed = true)
        every { savedStateHandle.get<Pair<Int, Int>?>("scrollPosition") } returns Pair(0, 0)

        coEvery { userPreferences.userToken } returns flowOf("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUllVzdjRldFUktuS0JVWVIiLCJpYXQiOjE3MTc1NTUwMTB9.4ccR_v-mgPAB3yKM4akhm-eVCv-xCbiZ59_atM1nOjM")

        val storyRepository = StoryRepository(apiServices, userPreferences)
        viewModel = HomeViewModel(
            storyRepository,
            authRepository,
            apiServices,
            userPreferences,
            savedStateHandle,
            differWrapper
        )
    }

    object DataDummy {
        fun generateDummyStories(): List<Story> {
            return List(20) { i ->
                Story(
                    createdAt = "2022-01-01T00:00:00Z",
                    description = "Description ${i + 1}",
                    id = "story_id_${i + 1}",
                    lat = -10.212 + i,
                    lon = -16.002 + i,
                    name = "Name $i",
                    photoUrl = "https://example.com/photo_${i + 1}.jpg"
                )
            }
        }
    }

    class FakePagingSource(private val data: List<Story>) : PagingSource<Int, Story>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
            return LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = null
            )
        }

        override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
            return null
        }
    }

    @Test
    fun allStoriesSuccessfullyLoaded() = runTest {
        val expectedStories = DataDummy.generateDummyStories()
        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.success(
            StoriesResponse(false, "Success", expectedStories)
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = object :
                androidx.recyclerview.widget.DiffUtil.ItemCallback<Story>() {
                override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean =
                    oldItem == newItem
            },
            updateCallback = noopListUpdateCallback,
            workerDispatcher = testDispatcher
        )

        val collectJob = launch {
            viewModel.allStories.collectLatest { receivedPagingData ->
                differ.submitData(receivedPagingData)
            }
        }

        delay(1000)
        val snapshot = differ.snapshot()

        assertNotNull("Received data should not be null", snapshot)

        assertEquals(
            "Expected number of items did not match",
            expectedStories.size,
            snapshot.size
        )

        if (snapshot.isNotEmpty()) {
            val firstItem = snapshot.items[0]
            assertEquals(
                "Expected first item's name did not match",
                expectedStories[0],
                firstItem
            )
        } else {
            fail("Snapshot is empty, cannot check first item")
        }

        collectJob.cancelAndJoin()
    }

    @Test
    fun whenNoStoryData_EnsureNoItemsReturned() = runTest {
        val emptyStories = emptyList<Story>()

        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.success(
            StoriesResponse(false, "No stories found", emptyStories)
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = object :
                androidx.recyclerview.widget.DiffUtil.ItemCallback<Story>() {
                override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean =
                    oldItem == newItem
            },
            updateCallback = noopListUpdateCallback,
            workerDispatcher = testDispatcher
        )

        val collectJob = launch {
            viewModel.allStories.collectLatest { receivedPagingData ->
                differ.submitData(receivedPagingData)
            }
        }
        delay(1000)
        val snapshot = differ.snapshot()
        assertEquals("Expected number of items should be zero", 0, snapshot.size)
        collectJob.cancelAndJoin()
    }

    @After
    fun tearDown() {
        viewModel.viewModelScope.cancel()
        Dispatchers.resetMain()
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
        }

        override fun onRemoved(position: Int, count: Int) {
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
        }
    }
}
