package com.brycecorbitt.artsyapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "ResponseHandler"

class ResponseHandler {
    private val appAPI: AppAPI

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://mbsr.wpi.edu:6567")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        appAPI = retrofit.create(AppAPI::class.java)
    }

    fun authenticate(tokenId: String): LiveData<AuthenticationResponse?> {
        val responseLiveData: MutableLiveData<AuthenticationResponse?> = MutableLiveData()
        val appRequest: Call<AuthenticationResponse?> = appAPI.authenticate(tokenId)
        appRequest.enqueue(object : Callback<AuthenticationResponse?> {
            override fun onFailure(call: Call<AuthenticationResponse?>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<AuthenticationResponse?>,
                response: Response<AuthenticationResponse?>
            ) {
                val item: AuthenticationResponse? = response.body()
                responseLiveData.value = item
            }
        })
        return responseLiveData
    }

    fun checkIfAuthenticated(): LiveData<AuthenticationResponse?> {
        val responseLiveData: MutableLiveData<AuthenticationResponse?> = MutableLiveData()
        val appRequest: Call<AuthenticationResponse?> = appAPI.checkIfAuthenticated()
        appRequest.enqueue(object : Callback<AuthenticationResponse?> {
            override fun onFailure(call: Call<AuthenticationResponse?>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<AuthenticationResponse?>,
                response: Response<AuthenticationResponse?>
            ) {
                val item: AuthenticationResponse? = response.body()
                responseLiveData.value = item
            }
        })
        return responseLiveData
    }

    fun deletePost(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val appRequest: Call<String> = appAPI.deleteAccount()
        appRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }

    fun signOut(): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val appRequest: Call<String> = appAPI.signOut()
        appRequest.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }
}