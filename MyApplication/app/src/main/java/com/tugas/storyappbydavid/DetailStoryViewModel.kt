package com.tugas.storyappbydavid

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugas.storyappbydavid.api.ApiConfig
import com.tugas.storyappbydavid.apiresponse.Story
import com.tugas.storyappbydavid.apiresponse.StoryResponse
import com.tugas.storyappbydavid.context.SuperApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailStoryViewModel : ViewModel() {
    private val sharedPrefFile = "kotlinsharedpreference"
    val contextAplikasi = SuperApplication.appContext

    private val _detailStory = MutableLiveData<Story?>()
    val detailStory : LiveData<Story?> = _detailStory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    companion object{
        private const val TAG = "DetailStoryViewModel"
    }

    fun getStory(id: String) {
        val sharedPreferences: SharedPreferences? = contextAplikasi?.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)
        val sharedNameValue = sharedPreferences?.getString("token","defaultname")
        _isLoading.value = true
        val client = ApiConfig().getApiService().getStory(id, "Bearer " + sharedNameValue.toString())
        client.enqueue(object : Callback<StoryResponse> {
            override fun onResponse(
                call: Call<StoryResponse>,
                response: Response<StoryResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailStory.value = response.body()?.story
                }
                else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<StoryResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}