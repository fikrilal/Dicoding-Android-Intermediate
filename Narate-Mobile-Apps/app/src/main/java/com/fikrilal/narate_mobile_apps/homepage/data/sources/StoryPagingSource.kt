package com.fikrilal.narate_mobile_apps.homepage.data.sources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fikrilal.narate_mobile_apps._core.data.api.ApiServices
import com.fikrilal.narate_mobile_apps._core.data.model.stories.Story

class StoryPagingSource(
    private val apiServices: ApiServices,
    private val token: String,
    private val location: Int
) : PagingSource<Int, Story>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        val pageNumber = params.key ?: 1
        Log.d("PagingSource", "Loading page: $pageNumber")
        return try {
            val response = apiServices.getAllStories(pageNumber, params.loadSize, location, "Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Log.d("PagingSource", "Page $pageNumber loaded successfully")
                LoadResult.Page(
                    data = response.body()!!.listStory,
                    prevKey = if (pageNumber == 1) null else pageNumber - 1,
                    nextKey = if (response.body()!!.listStory.isEmpty()) null else pageNumber + 1
                )
            } else {
                Log.e("PagingSource", "Error loading page $pageNumber")
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (e: Exception) {
            Log.e("PagingSource", "Load failure", e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}