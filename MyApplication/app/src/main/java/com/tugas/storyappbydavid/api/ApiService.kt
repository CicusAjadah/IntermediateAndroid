package com.tugas.storyappbydavid.api

import com.tugas.storyappbydavid.apiresponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("/v1/register")
    fun registerAccount(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/v1/login")
    fun loginAccount(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("/v1/stories")
    fun getStories(
        @Header("Authorization") authheader: String,
        @Query("location") location : Int
    ): Call<ListStoriesResponse>

    @GET("/v1/stories")
    suspend fun getStoriesPaging(
        @Header("Authorization") authheader: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ListStoriesResponse>

    @GET("/v1/stories/{id}")
    fun getStory(
        @Path("id") id: String, @Header("Authorization") authheader: String
    ): Call<StoryResponse>

    @Multipart
    @POST("/v1/stories")
    fun uploadStory(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
        @Header("Authorization") authheader: String
    ): Call<UploadResponse>
}