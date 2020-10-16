package com.brycecorbitt.artsyapp.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.brycecorbitt.artsyapp.PostRepository
import com.brycecorbitt.artsyapp.PostTemp
import com.brycecorbitt.artsyapp.api.Post
import com.brycecorbitt.artsyapp.api.ResponseHandler

class FeedViewModel : ViewModel() {
    //val posts = ArrayList<Post>()

    private val postRepository = PostRepository.get()
    var postListLiveData = postRepository.getPosts()

}