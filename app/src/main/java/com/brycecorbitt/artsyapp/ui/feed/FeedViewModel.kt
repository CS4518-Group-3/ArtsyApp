package com.brycecorbitt.artsyapp.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brycecorbitt.artsyapp.PostRepository
import com.brycecorbitt.artsyapp.LocationService
import com.brycecorbitt.artsyapp.PostTemp
import com.brycecorbitt.artsyapp.api.Post
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.ui.preferences.PreferencesViewModel

class FeedViewModel : ViewModel() {
    val pref = PreferencesViewModel.get()
    val posts = ArrayList<Post>()
    var page = 1
    var limit = 10

    private val postRepository = PostRepository.get()
    var postListLiveData = postRepository.getPosts(page, limit)

    init {
        for (i in 0 until 100) {
            val post = Post(0.0f,0.0f,"")
            posts += post
        }
    }

}