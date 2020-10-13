package com.brycecorbitt.artsyapp.api

import retrofit2.Call
import retrofit2.http.*

interface AppAPI {
    //AUTH ENDPOINTS
    @GET("/auth/callback")
    fun authenticate(@Query("id_token") id_token: String): Call<AuthenticationResponse?>

    @GET("/auth/check")
    fun checkIfAuthenticated(): Call<AuthenticationResponse?>

    @DELETE("/auth/account")
    fun deleteAccount(): Call<String>

    @GET("/auth/signout")
    fun signOut(): Call<String>





    //POST ENDPOINTS
    @POST("/post")
    fun createPost(@Query("lat") lat: Float?,
                   @Query("lon") lon: Float?/*,
                   @Query("content") content: content?*/): Call<Post>

    @DELETE("/post/:id")
    fun deletePost(@Query("id") id: String?): Call<String>

    @GET("/post/:id/upvote")
    fun upvote(@Query("id") id: String?): Call<String>

    @GET("/post/:id/downvote")
    fun downvote(@Query("id") id: String?): Call<String>

    @GET("/post/:id")
    fun getPost(@Query("id") id: String?): Call<String>

    @GET("/post/feed")
    fun getFeed(@Query("lat") lat: Float,
                @Query("lon") lon: Float,
                @Query("radius") radius: Number,
                @Query("unit") unit: String,
                @Query("sort_by") sort_by: String?,
                @Query("page") page: Int?,
                @Query("limit") limit: Int?): Call<String>

}