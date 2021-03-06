package com.brycecorbitt.artsyapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brycecorbitt.artsyapp.api.Post
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.api.User
import com.brycecorbitt.artsyapp.ui.preferences.PreferencesViewModel

import java.lang.IllegalStateException

class PostRepository private constructor(context: Context) {

    private var appCaller: ResponseHandler = ResponseHandler(context)
    private var preferencesViewModel = PreferencesViewModel.get()

    fun getPosts(page: Int, limit: Int): LiveData<List<Post>> {
        val location = LocationService.current_location
        val local = preferencesViewModel.checkIsLocal
        var lon: Float = 42.270634F
        var lat: Float = -71.80286F

        if(local && location != null){
            lat = location.latitude.toFloat()
            lon = location.longitude.toFloat()
        }
        else {
            lat = preferencesViewModel.currentGlobalLat
            lon = preferencesViewModel.currentGlobalLon
        }
        return appCaller.getFeed(lat, lon, preferencesViewModel.currentRadius,
        preferencesViewModel.currentUnit, preferencesViewModel.CurrentSortType,page,100000)}

    fun getUserPosts(page: Int, limit: Int): LiveData<List<Post>> {

        return appCaller.getUserFeed(page, limit)}

    companion object {
        private var INSTANCE: PostRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PostRepository(context)
            }
        }

        fun get(): PostRepository {
            return INSTANCE ?:
            throw IllegalStateException("PostRepository must be initialized")
        }
    }
}