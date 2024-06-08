package com.fikrilal.narate_mobile_apps.homepage.viewmodel

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import androidx.paging.PagingSource
import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story
import com.fikrilal.narate_mobile_apps._core.data.model.stories.StoriesResponse
import com.fikrilal.narate_mobile_apps.homepage.data.sources.StoryPagingSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import retrofit2.Response

class StoryPagingSourceTest {

    private lateinit var apiServices: ApiServices
    private lateinit var storyPagingSource: StoryPagingSource

    @Before
    fun setup() {
        apiServices = mockk()
        storyPagingSource = StoryPagingSource(apiServices, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUllVzdjRldFUktuS0JVWVIiLCJpYXQiOjE3MTc1NTUwMTB9.4ccR_v-mgPAB3yKM4akhm-eVCv-xCbiZ59_atM1nOjM", 0)
    }

    @Test
    fun `test story paging source loads data successfully`() = runTest {
        val stories = listOf(Story("2022-01-01T00:00:00Z", "Lorem Ipsum", "story-id", -10.212, -16.002, "Dimas", "url"))
        val storiesResponse = StoriesResponse(false, "Success", stories)
        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.success(storiesResponse)
        val result = storyPagingSource.load(PagingSource.LoadParams.Refresh(null, 20, false))
        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(stories, (result as PagingSource.LoadResult.Page).data)
    }

    @Test
    fun `test story paging source handles errors`() = runTest {
        coEvery { apiServices.getAllStories(any(), any(), any(), any()) } returns Response.error(400, okhttp3.ResponseBody.create(null, "Bad Request"))
        val result = storyPagingSource.load(PagingSource.LoadParams.Refresh(null, 20, false))
        assertTrue(result is PagingSource.LoadResult.Error)
    }
}
