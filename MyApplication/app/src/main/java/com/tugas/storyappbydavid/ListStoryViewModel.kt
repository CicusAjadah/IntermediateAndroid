package com.tugas.storyappbydavid

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tugas.storyappbydavid.api.ApiConfig
import com.tugas.storyappbydavid.apiresponse.ListStoriesResponse
import com.tugas.storyappbydavid.apiresponse.ListStoryItem
import com.tugas.storyappbydavid.data.StoryRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListStoryViewModel(storyRepository: StoryRepository) : ViewModel() {

    val stories : LiveData<PagingData<ListStoryItem>> = storyRepository.getStories().cachedIn(viewModelScope)

    private val _listStoriesWithLocation = MutableLiveData<List<ListStoryItem?>?>()
    val listStoriesWithLocation : LiveData<List<ListStoryItem?>?> = _listStoriesWithLocation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        private const val TAG = "ListStoryViewModel"
    }

    fun getStoriesWithLocation(header: String) {
        _isLoading.value = true
        val client = ApiConfig().getApiService().getStories(header, 1)
        client.enqueue(object : Callback<ListStoriesResponse> {
            override fun onResponse(
                call: Call<ListStoriesResponse>,
                response: Response<ListStoriesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listStoriesWithLocation.value = response.body()?.listStory
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ListStoriesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}