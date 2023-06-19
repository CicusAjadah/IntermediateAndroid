package com.tugas.storyappbydavid

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugas.storyappbydavid.api.ApiConfig
import com.tugas.storyappbydavid.apiresponse.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    private val _tokenMasuk = MutableLiveData<String>()
    val tokenMasuk : LiveData<String?> = _tokenMasuk

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "LoginViewModel"
    }

    fun loginAccount(email:String, password:String) {
        _isLoading.value = true
        val client = ApiConfig().getApiService().loginAccount(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    _tokenMasuk.value = response.body()?.loginResult?.token.toString()
                    Log.e(ContentValues.TAG, tokenMasuk.value.toString())
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message}")
            }
        })
    }
}