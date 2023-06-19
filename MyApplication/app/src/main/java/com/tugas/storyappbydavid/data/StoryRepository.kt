package com.tugas.storyappbydavid.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.tugas.storyappbydavid.api.ApiService
import com.tugas.storyappbydavid.apiresponse.ListStoryItem

class StoryRepository(private val apiService: ApiService) {
    fun getStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                StoryPagingSource(apiService)
            }
        ).liveData
    }
}