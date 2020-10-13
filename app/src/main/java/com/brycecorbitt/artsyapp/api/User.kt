package com.brycecorbitt.artsyapp.api

import com.google.gson.annotations.SerializedName

class User {
    @SerializedName("created_at")
    lateinit var created_at: String

    @SerializedName("email")
    lateinit var email: String

    @SerializedName("google_id")
    lateinit var google_id: String

    @SerializedName("id")
    lateinit var id: String

    @SerializedName("name")
    lateinit var name: String

    @SerializedName("profile_url")
    lateinit var profile_url: String

    @SerializedName("updated_at")
    lateinit var updated_at: String
}