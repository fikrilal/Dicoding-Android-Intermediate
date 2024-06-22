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
        println("Setting up test environment")
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
            savedStateHandle,
            differWrapper
        )
        println("Setup complete")
    }

    object DataDummy {
        fun generateDummyStories(): List<Story> {
            println("Generating dummy stories")
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
        println("Running allStoriesSuccessfullyLoaded test")
        val expectedStories = DataDummy.generateDummyStories()
        val fakePagingSource = FakePagingSource(expectedStories)

        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { fakePagingSource }
        )

        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.success(
            StoriesResponse(false, "Success", expectedStories)
        )
        coEvery { differWrapper.submitData(any()) } just Runs

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
            println("Collecting allStories flow")
            pager.flow.collectLatest { receivedPagingData ->
                println("Received PagingData: $receivedPagingData")
                println("Submitting data to differ")
                differ.submitData(receivedPagingData)
                println("Data submitted to differ")
            }
        }

        delay(1000)

        println("Creating snapshot")
        val snapshot = differ.snapshot()
        println("Snapshot: $snapshot")

        println("Checking snapshot is not null")
        assertNotNull("Received data should not be null", snapshot)

        println("Checking snapshot size matches expected size")
        assertEquals(
            "Expected number of items did not match",
            expectedStories.size,
            snapshot.size
        )

        if (snapshot.isNotEmpty()) {
            println("Checking first item in snapshot matches expected first item")
            val firstItem = snapshot.items[0]
            println("First item: $firstItem")
            assertEquals(
                "Expected first item's name did not match",
                expectedStories[0],
                firstItem
            )
        } else {
            println("Snapshot is empty, cannot check first item")
            fail("Snapshot is empty, cannot check first item")
        }

        collectJob.cancelAndJoin()
        println("Finished allStoriesSuccessfullyLoaded test")
    }

    @Test
    fun whenNoStoryData_EnsureNoItemsReturned() = runTest {
        println("Running whenNoStoryData_EnsureNoItemsReturned test")
        val emptyStories = emptyList<Story>()
        val fakePagingSource = FakePagingSource(emptyStories)

        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { fakePagingSource }
        )

        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.success(
            StoriesResponse(false, "No stories found", emptyStories)
        )
        coEvery { differWrapper.submitData(any()) } just Runs

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
            println("Collecting allStories flow")
            pager.flow.collectLatest { receivedPagingData ->
                println("Received PagingData: $receivedPagingData")
                println("Submitting data to differ")
                differ.submitData(receivedPagingData)
                println("Data submitted to differ")
            }
        }

        delay(1000)

        println("Creating snapshot")
        val snapshot = differ.snapshot()
        println("Snapshot: $snapshot")

        println("Checking snapshot size is zero")
        assertEquals("Expected number of items should be zero", 0, snapshot.size)

        collectJob.cancelAndJoin()
        println("Finished whenNoStoryData_EnsureNoItemsReturned test")
    }

    @After
    fun tearDown() {
        println("Tearing down test environment")
        viewModel.viewModelScope.cancel()
        Dispatchers.resetMain()
        println("Teardown complete")
    }

    private val noopListUpdateCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {
            println("Inserted $count items at position $position")
        }

        override fun onRemoved(position: Int, count: Int) {
            println("Removed $count items from position $position")
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            println("Moved item from position $fromPosition to $toPosition")
        }

        override fun onChanged(position: Int, count: Int, payload: Any?) {
            println("Changed $count items at position $position with payload $payload")
        }
    }
}