package com.brycecorbitt.artsyapp.ui.feed

import androidx.lifecycle.ViewModel
import com.brycecorbitt.artsyapp.PostTemp

class FeedViewModel : ViewModel() {
    val posts = ArrayList<PostTemp>()

    init {
        for (i in 0 until 100) {
            val post = PostTemp()
            post.index = i
            posts += post
        }
    }
}