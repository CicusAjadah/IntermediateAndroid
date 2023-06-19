package com.tugas.storyappbydavid

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugas.storyappbydavid.api.ApiConfig
import com.tugas.storyappbydavid.apiresponse.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UploadStoryViewModel : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status : LiveData<Boolean> = _status

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "UploadStoryViewModel"
    }

    fun uploadStory(imageMultipart:MultipartBody.Part, description:RequestBody, header:String) {
        _isLoading.value = true
        val service = ApiConfig().getApiService().uploadStory(imageMultipart, description, header)
        service.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _status.value = true
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error!!) {
                        Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")

                }
            }
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}