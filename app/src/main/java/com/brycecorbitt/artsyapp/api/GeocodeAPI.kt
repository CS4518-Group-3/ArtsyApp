package com.brycecorbitt.artsyapp.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeAPI {

    @GET("/v1/reverse.php")
    fun geoAddress(
        @Query("key") key: String,
        @Query("lat") lat: Float,
        @Query("lon") lon: Float,
        @Query("format") format: String
    ): Call<GeoAddress>
}