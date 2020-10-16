package com.brycecorbitt.artsyapp.api

import retrofit2.Call
import retrofit2.http.*


interface AppAPI {
    //AUTH ENDPOINTS
    @GET("/auth/callback")
    fun authenticate(
        @Query("id_token") id_token: String
    ): Call<AuthenticationResponse?>

    @GET("/auth/check")
    fun checkIfAuthenticated(): Call<AuthenticationResponse?>

    @DELETE("/auth/account")
    fun deleteAccount(): Call<Void>

    @GET("/auth/signout")
    fun signOut(): Call<Void>


    //POST ENDPOINTS
    @FormUrlEncoded
    @POST("/post")
    fun createPost(
        @Field("lat") lat: Float?,
        @Field("lon") lon: Float?,
        @Field("content") content: String?
    ): Call<Post>

    @DELETE("/post/{id}")
    fun deletePost(@Path("id") id: String?): Call<Void>?

    @GET("/post/{id}/upvote")
    fun upvote(@Path("id") id: String?): Call<VoteData>?



    @GET("/post/{id}/downvote")
    fun downvote(@Path("id") id: String?): Call<VoteData>?

    @GET("/post/:id")
    fun getPost(@Query("id") id: String?): Call<Post>

    @GET("/post/feed")
    fun getFeed(
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("radius") radius: Number,
        @Query("unit") unit: String,
        @Query("sort_by") sort_by: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<List<Post>>

    @GET("/post/user")
    fun getUserFeed(
        @Query("page") page: Int?,
        @Query("limit") limit: Int?
    ): Call<List<Post>>

}