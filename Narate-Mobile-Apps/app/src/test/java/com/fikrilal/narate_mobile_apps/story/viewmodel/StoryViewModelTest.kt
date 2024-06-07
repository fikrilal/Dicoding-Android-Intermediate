//package com.fikrilal.narate_mobile_apps.story.viewmodel
//
//import android.content.ContentResolver
//import android.content.Context
//import android.net.Uri
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.lifecycle.Observer
//import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoryDetailResponse
//import com.fikrilal.narate_mobile_apps._core.data.repository.story.StoryRepository
//import com.fikrilal.narate_mobile_apps.story.data.utils.CompressImageUtils
//import com.fikrilal.narate_mobile_apps.story.presentation.viewmodel.StoryViewModel
//import io.mockk.*
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.annotation.Config
//import java.io.ByteArrayInputStream
//import java.io.File
//import java.io.InputStream
//
//@ExperimentalCoroutinesApi
//@RunWith(RobolectricTestRunner::class)
//@Config(manifest = Config.NONE)
//class StoryViewModelTest {
//
//    @get:Rule
//    val instantExecutorRule = InstantTaskExecutorRule()
//
//    private lateinit var storyRepository: StoryRepository
//    private lateinit var context: Context
//    private lateinit var contentResolver: ContentResolver
//    private lateinit var storyViewModel: StoryViewModel
//    private lateinit var uri: Uri
//    private lateinit var inputStream: InputStream
//
//    @Before
//    fun setUp() {
//        storyRepository = mockk()
//        context = mockk()
//        contentResolver = mockk()
//        storyViewModel = StoryViewModel(storyRepository, context)
//        uri = mockk()
//        inputStream = ByteArrayInputStream(byteArrayOf()) // Mocked InputStream
//
//        // Mock context and content resolver
//        every { context.contentResolver } returns contentResolver
//        every { contentResolver.openInputStream(any()) } returns inputStream
//        every { inputStream.read(any<ByteArray>()) } returns -1 // Mock end of stream
//        every { inputStream.close() } just Runs // Mock close operation
//    }
//
//    @Test
//    fun `setImageUri sets image URI correctly`() {
//        val observer = mockk<Observer<Uri?>>(relaxed = true)
//
//        storyViewModel.imageUri.observeForever(observer)
//        storyViewModel.setImageUri(uri)
//
//        verify { observer.onChanged(uri) }
//        storyViewModel.imageUri.removeObserver(observer)
//    }
//
//    @Test
//    fun `setText sets text correctly`() {
//        val observer = mockk<Observer<String>>(relaxed = true)
//        val text = "Test text"
//
//        storyViewModel.text.observeForever(observer)
//        storyViewModel.setText(text)
//
//        verify { observer.onChanged(text) }
//        storyViewModel.text.removeObserver(observer)
//    }
//
//    @Test
//    fun `uploadStory uploads story successfully`() = runTest {
//        val observer = mockk<Observer<Result<StoryDetailResponse>>>(relaxed = true)
//        val text = "Test text"
//        val file = mockk<File>()
//        val response = mockk<StoryDetailResponse>()
//        val byteArray = byteArrayOf()
//
//        every { CompressImageUtils.uriToBitmap(any(), any()) } returns mockk()
//        every { CompressImageUtils.resizeBitmap(any(), any()) } returns mockk()
//        every { CompressImageUtils.compressBitmap(any(), any()) } returns byteArray
//        every { storyViewModel.byteArrayToFile(any(), any(), any()) } returns file
//        coEvery { storyRepository.userPreferences.getUserToken() } returns "testToken"
//        coEvery { storyRepository.addNewStory(any(), any(), any(), any(), any()) } returns response
//
//        storyViewModel.imageUri.observeForever { }
//        storyViewModel.setText(text)
//        storyViewModel.setImageUri(uri)
//        storyViewModel.uploadResult.observeForever(observer)
//
//        storyViewModel.uploadStory()
//
//        coVerify { storyRepository.addNewStory(text, file, null, null, "Bearer testToken") }
//        verify { observer.onChanged(Result.success(response)) }
//
//        storyViewModel.uploadResult.removeObserver(observer)
//    }
//
//    @Test
//    fun `uploadStory handles error during upload`() = runTest {
//        val observer = mockk<Observer<Result<StoryDetailResponse>>>(relaxed = true)
//        val text = "Test text"
//        val file = mockk<File>()
//        val exception = Exception("Test exception")
//        val byteArray = byteArrayOf()
//
//        every { CompressImageUtils.uriToBitmap(any(), any()) } returns mockk()
//        every { CompressImageUtils.resizeBitmap(any(), any()) } returns mockk()
//        every { CompressImageUtils.compressBitmap(any(), any()) } returns byteArray
//        every { storyViewModel.byteArrayToFile(any(), any(), any()) } returns file
//        coEvery { storyRepository.userPreferences.getUserToken() } returns "testToken"
//        coEvery { storyRepository.addNewStory(any(), any(), any(), any(), any()) } throws exception
//
//        storyViewModel.imageUri.observeForever { }
//        storyViewModel.setText(text)
//        storyViewModel.setImageUri(uri)
//        storyViewModel.uploadResult.observeForever(observer)
//
//        storyViewModel.uploadStory()
//
//        verify { observer.onChanged(Result.failure(exception)) }
//
//        storyViewModel.uploadResult.removeObserver(observer)
//    }
//}