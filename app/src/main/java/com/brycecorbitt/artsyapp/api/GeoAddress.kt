package com.brycecorbitt.artsyapp.api

import com.google.gson.annotations.SerializedName

class GeoAddress {
    @SerializedName("address")
    lateinit var addressComponents: AddressComponents
}