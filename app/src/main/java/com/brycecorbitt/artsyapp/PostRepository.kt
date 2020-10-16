package com.brycecorbitt.artsyapp

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.brycecorbitt.artsyapp.api.Post
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.ui.preferences.PreferencesViewModel

import java.lang.IllegalStateException

class PostRepository private constructor(context: Context) {

    private var appCaller: ResponseHandler = ResponseHandler(context)
    private var preferencesViewModel = PreferencesViewModel.get()

    fun getPosts(): LiveData<List<Post>> = appCaller.getFeed(preferencesViewModel.currentLat, preferencesViewModel.currentLon, preferencesViewModel.currentRadius,
        preferencesViewModel.currentUnit, preferencesViewModel.CurrentSortType,1,10)

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