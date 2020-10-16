package com.brycecorbitt.artsyapp.ui.feed

import androidx.lifecycle.ViewModel
import com.brycecorbitt.artsyapp.PostTemp
import com.brycecorbitt.artsyapp.api.Post

class FeedViewModel : ViewModel() {
    val posts = ArrayList<Post>()

    init {
        for (i in 0 until 100) {
            val post = Post(0.0f,0.0f,"")
            posts += post
        }
    }
}