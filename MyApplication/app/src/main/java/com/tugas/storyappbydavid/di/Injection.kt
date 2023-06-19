package com.tugas.storyappbydavid.di

import android.content.Context
import com.tugas.storyappbydavid.api.ApiConfig
import com.tugas.storyappbydavid.data.StoryRepository

object Injection {
    fun provideRepository(context: Context): StoryRepository {
        val apiService = ApiConfig().getApiService()
        return StoryRepository(apiService)
    }
}