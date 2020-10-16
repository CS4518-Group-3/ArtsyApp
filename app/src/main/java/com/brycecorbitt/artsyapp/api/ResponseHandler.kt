package com.brycecorbitt.artsyapp.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "ResponseHandler"

class ResponseHandler(context: Context?) {
    private val appAPI: AppAPI
    private var preferences = Pref.get(context)
    private val geocodeAPI: GeocodeAPI

    init {
        val client = OkHttpClient().newBuilder()
        client.addInterceptor(HeaderInterceptor(context))
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        client.addInterceptor(logging)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://mbsr.wpi.edu:6567")
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        appAPI = retrofit.create(AppAPI::class.java)

        val geoRetrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://us1.locationiq.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        geocodeAPI = geoRetrofit.create(GeocodeAPI::class.java)
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
                val cookie: String? = response.headers().get("Set-Cookie")
                var editor = preferences?.editor
                editor?.putString("cookie", cookie)
                editor?.commit()

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

    fun deleteAccount(): LiveData<Void> {
        val responseLiveData: MutableLiveData<Void> = MutableLiveData()
        val appRequest: Call<Void> = appAPI.deleteAccount()
        appRequest.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                responseLiveData.value = response.body()
                var editor = preferences?.editor
                editor?.clear()?.commit()
            }
        })
        return responseLiveData
    }

    fun signOut(): LiveData<Void> {
        val responseLiveData: MutableLiveData<Void> = MutableLiveData()
        val appRequest: Call<Void> = appAPI.signOut()
        appRequest.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }

    fun createPost(lat: Float?, lon: Float, content: String?): LiveData<Post> {
        val responseLiveData: MutableLiveData<Post> = MutableLiveData()
        val appRequest: Call<Post> = appAPI.createPost(lat, lon, content)
        appRequest.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }

    fun deletePost(id: String?): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val appRequest: Call<String> = appAPI.deletePost(id)
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

    fun upvote(id: String?): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val appRequest: Call<String> = appAPI.upvote(id)
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

    fun downvote(id: String?): LiveData<String> {
        val responseLiveData: MutableLiveData<String> = MutableLiveData()
        val appRequest: Call<String> = appAPI.downvote(id)
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

    fun getPost(id: String?): LiveData<Post> {
        val responseLiveData: MutableLiveData<Post> = MutableLiveData()
        val appRequest: Call<Post> = appAPI.getPost(id)
        appRequest.enqueue(object : Callback<Post> {
            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<Post>,
                response: Response<Post>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }

    fun getFeed(lat: Float, lon: Float, radius: Number, unit: String, sort_by: String?, page: Int?, limit: Int?): LiveData<List<Post>> {
        val responseLiveData: MutableLiveData<List<Post>> = MutableLiveData()
        val appRequest: Call<List<Post>> = appAPI.getFeed(lat, lon, radius, unit, sort_by, page, limit)
        appRequest.enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }


    fun getUserFeed(page: Int?, limit: Int?): LiveData<List<Post>> {
        val responseLiveData: MutableLiveData<List<Post>> = MutableLiveData()
        val appRequest: Call<List<Post>> = appAPI.getUserFeed(page, limit)
        appRequest.enqueue(object : Callback<List<Post>> {
            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<List<Post>>,
                response: Response<List<Post>>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }

    fun geoAddress(key: String, lat: Float, lon: Float, format: String): LiveData<GeoAddress> {
        val responseLiveData: MutableLiveData<GeoAddress> = MutableLiveData()
        val appRequest: Call<GeoAddress> = geocodeAPI.geoAddress(key, lat, lon, format)
        appRequest.enqueue(object : Callback<GeoAddress> {
            override fun onFailure(call: Call<GeoAddress>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<GeoAddress>,
                response: Response<GeoAddress>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }
}