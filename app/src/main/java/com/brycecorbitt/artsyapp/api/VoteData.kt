package com.brycecorbitt.artsyapp.api

import com.google.gson.annotations.SerializedName

class VoteData {

    // enum
    @SerializedName("vote_status")
    lateinit var vote_status: Post.VoteStatus


    //netvotes
    @SerializedName("score")
    var score: Int = 0
}