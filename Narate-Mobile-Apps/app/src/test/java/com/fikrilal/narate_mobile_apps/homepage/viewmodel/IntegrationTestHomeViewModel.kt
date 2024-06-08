//package com.fikrilal.narate_mobile_apps.homepage.viewmodel
//
//import android.util.Log
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.paging.PagingData
//import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
//import com.fikrilal.narate_mobile_apps._core.data.repository.auth.AuthRepository
//import com.fikrilal.narate_mobile_apps._core.data.repository.auth.UserPreferences
//import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
//import com.fikrilal.narate_mobile_apps.homepage.presentation.viewmodel.HomeViewModel
//import io.mockk.coEvery
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.mockkStatic
//import junit.framework.TestCase.assertNotNull
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runBlockingTest
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//@ExperimentalCoroutinesApi
//class IntegrationTestHomeViewModel {
//
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()
//    private val testDispatcher = TestCoroutineDispatcher()
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var apiServices: ApiServices
//    private lateinit var userPreferences: UserPreferences
//    private lateinit var authRepository: AuthRepository
//    private lateinit var storyRepository: StoryRepository
//
//    @Before
//    fun setup() {
//        mockkStatic(Log::class)
//        every { Log.d(any(), any()) } returns 0
//        every { Log.e(any(), any(), any()) } returns 0
//        every { Log.isLoggable(any(), any()) } returns false
//        Dispatchers.setMain(testDispatcher)
//        apiServices = mockk(relaxed = true)
//        userPreferences = mockk(relaxed = true)
//        authRepository = mockk(relaxed = true)
//        storyRepository = StoryRepository(apiServices, userPreferences)
//
//        // Mock the user token flow
//        coEvery { userPreferences.userToken } returns flowOf("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUllVzdjRldFUktuS0JVWVIiLCJpYXQiOjE3MTc1NTUwMTB9.4ccR_v-mgPAB3yKM4akhm-eVCv-xCbiZ59_atM1nOjM")
//
//        viewModel = HomeViewModel(storyRepository, authRepository, apiServices, userPreferences)
//    }
//    @After
//    fun tearDown() {
//        // Reset the main dispatcher to the original Main dispatcher
//        Dispatchers.resetMain()
//        // Clean up the dispatcher if needed
//        testDispatcher.cleanupTestCoroutines()
//    }
//    @Test
//    fun `test HomeViewModel processes PagingData correctly`() = testDispatcher.runBlockingTest {
//        // Assuming allStories is a Flow<PagingData<Story>>
//        val pagingData = viewModel.allStories.first()
//        assertNotNull(pagingData.toString(), "PagingData should not be null")
//
//        // Additional assertions can be added here to check for specific data characteristics or behaviors
//    }
//
//    // More tests can be added here to simulate different scenarios, such as changes in user token,
//    // handling of different API responses, etc.
//}
