package com.tugas.storyappbydavid.data


import android.content.Context
import android.content.SharedPreferences
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tugas.storyappbydavid.api.ApiService
import com.tugas.storyappbydavid.apiresponse.ListStoryItem
import com.tugas.storyappbydavid.context.SuperApplication

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {
    private val sharedPrefFile = "kotlinsharedpreference"
    val contextAplikasi = SuperApplication.appContext

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        val sharedPreferences: SharedPreferences? = contextAplikasi?.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences?.getString("token","defaultname")

        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getStoriesPaging("Bearer " + sharedNameValue.toString(), position, params.loadSize)
            if (response.isSuccessful) {
                val responseData = response.body()?.listStory

                LoadResult.Page(
                    data = responseData!!.filterNotNull(),
                    prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                    nextKey = if (responseData.isNullOrEmpty()) null else position + 1
                )
            } else {
                LoadResult.Error(Exception("Failed to load data"))
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}