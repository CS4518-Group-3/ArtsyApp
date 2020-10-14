package com.brycecorbitt.artsyapp.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "ResponseHandler"

class ResponseHandler(context: Context) {
    private val appAPI: AppAPI
    private var preferences = Pref.get(context)

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
                var editor = preferences?.editor
                var gson = Gson()
                var str = gson.toJson(item)
                editor?.putString("cookie", str)
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

    fun deleteAccount(): LiveData<String> {
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

    fun getFeed(lat: Float, lon: Float, radius: Number, unit: String, sort_by: String?, page: Int?, limit: Int?): LiveData<Array<Post>> {
        val responseLiveData: MutableLiveData<Array<Post>> = MutableLiveData()
        val appRequest: Call<Array<Post>> = appAPI.getFeed(lat, lon, radius, unit, sort_by, page, limit)
        appRequest.enqueue(object : Callback<Array<Post>> {
            override fun onFailure(call: Call<Array<Post>>, t: Throwable) {
                Log.e(TAG, "Failed!", t)
            }

            override fun onResponse(
                call: Call<Array<Post>>,
                response: Response<Array<Post>>
            ) {
                responseLiveData.value = response.body()
            }
        })
        return responseLiveData
    }
}