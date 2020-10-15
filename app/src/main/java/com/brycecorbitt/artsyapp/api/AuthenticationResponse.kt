package com.brycecorbitt.artsyapp.api

import com.google.gson.annotations.SerializedName

class AuthenticationResponse {
    @SerializedName("authenticated")
    var authenticated: Boolean? = false

    @SerializedName("user")
    var user: User? = null
}