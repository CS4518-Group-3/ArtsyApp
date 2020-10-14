package com.brycecorbitt.artsyapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.PostsAdapter
import com.brycecorbitt.artsyapp.R

private const val TAG = "FeedFragment"

class FeedFragment : Fragment() {

    private lateinit var feedListView: ListView

    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total posts: ${feedViewModel.posts.size}")
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feed, container, false)
        feedListView = root.findViewById(R.id.post_list_view) as ListView
        val posts = feedViewModel.posts
        val  postsAdapter = PostsAdapter(posts, activity!!)
        feedListView.adapter = postsAdapter

        return root
    }
}