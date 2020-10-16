package com.brycecorbitt.artsyapp.api

import com.google.gson.annotations.SerializedName

class VoteData {

    // enum
    @SerializedName("vote_status")
    var vote_status: Int = 0


    //netvotes
    @SerializedName("score")
    var score: Int = 0
}