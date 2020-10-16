package com.brycecorbitt.artsyapp.api

import com.google.gson.annotations.SerializedName

class Post(lat: Float?, lon: Float?, content: String?) {
    @SerializedName("content")
    var content: String? = null

    @SerializedName("created_at")
    var created_at: String? = null

    @SerializedName("distance")
    var distance: Float? = 0.0f

    @SerializedName("distance_unit")
    var distance_unit: String? = null

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("lat")
    var lat: Float = 0.0f

    @SerializedName("lon")
    var lon: Float = 0.0f

    @SerializedName("owned")
    var owned: Boolean? = false

    @SerializedName("score")
    var score: Int = 0

    @SerializedName("updated_at")
    var updated_at: String? = null

    @SerializedName("vote_status")
    var status: VoteStatus? = null

    enum class VoteStatus {
        @SerializedName("0")
        unvoted,
        @SerializedName("1")
        upvoted,
        @SerializedName("2")
        downvoted
    }
}