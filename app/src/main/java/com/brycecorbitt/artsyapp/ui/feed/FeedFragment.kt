package com.brycecorbitt.artsyapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.brycecorbitt.artsyapp.PostRepository
import com.brycecorbitt.artsyapp.PostsAdapter
import com.brycecorbitt.artsyapp.R
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.api.User
import com.brycecorbitt.artsyapp.ui.preferences.PreferencesViewModel

private const val TAG = "FeedFragment"

class FeedFragment : Fragment() {

    private lateinit var feedListView: ListView
    private lateinit var browsingModeButton: ImageButton
    private lateinit var locationTextView: TextView
    private lateinit var radiusLayout: LinearLayout
    private lateinit var radiusTextView: TextView
    private lateinit var refreshButton: ImageButton
    private lateinit var preferencesViewModel: PreferencesViewModel
    private lateinit var adapter: PostsAdapter
    private var page: Int = 1
    private var limit: Int = 100000

    private val feedViewModel: FeedViewModel by lazy {
        ViewModelProviders.of(this).get(FeedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesViewModel = PreferencesViewModel.get()
        //Log.d(TAG, "Total posts: ${feedViewModel.posts.size}")
    }

    override fun onStart() {
        super.onStart()

        browsingModeButton.setOnClickListener {
            if (preferencesViewModel.checkIsLocal) {
                preferencesViewModel.setIsLocal(false)
                browsingModeButton.setBackgroundResource(R.drawable.ic_global_browsing)
                feedViewModel.postListLiveData = feedViewModel.postRepository.getPosts(page, limit)
                feedViewModel.postListLiveData.observe(
                    viewLifecycleOwner,
                    Observer { posts ->
                        posts?.let {
                            Log.i(TAG, "Got posts ${posts.size}")

                            feedListView.adapter = PostsAdapter(posts, activity!!, viewLifecycleOwner)
                        }
                    }
                )
            } else {
                preferencesViewModel.setIsLocal(true)
                browsingModeButton.setBackgroundResource(R.drawable.ic_local_browsing)
                feedViewModel.postListLiveData = feedViewModel.postRepository.getPosts(page, limit)
                feedViewModel.postListLiveData.observe(
                    viewLifecycleOwner,
                    Observer { posts ->
                        posts?.let {
                            Log.i(TAG, "Got posts ${posts.size}")

                            feedListView.adapter = PostsAdapter(posts, activity!!, viewLifecycleOwner)
                        }
                    }
                )
            }
        }

        //TODO: check/update location TextView

        radiusLayout.setOnClickListener {
            val navController = findNavController(activity!!, R.id.nav_host_fragment)
            navController.navigate(R.id.navigation_preferences)
        }

        radiusTextView.text = preferencesViewModel.currentRadius.toInt().toString()

        refreshButton.setOnClickListener {
            feedViewModel.postListLiveData = feedViewModel.postRepository.getPosts(page, limit)
            feedViewModel.postListLiveData.observe(
                viewLifecycleOwner,
                Observer { posts ->
                    posts?.let {
                        Log.i(TAG, "Got posts ${posts.size}")

                        feedListView.adapter = PostsAdapter(posts, activity!!, viewLifecycleOwner)
                    }
                }
            )

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_feed, container, false)
        feedListView = root.findViewById(R.id.post_list_view) as ListView
//        feedListView.setOnScrollListener(object :AbsListView.OnScrollListener {
//
//        })

        browsingModeButton = root.findViewById(R.id.browsing_mode)
        locationTextView = root.findViewById(R.id.location)
        radiusLayout = root.findViewById(R.id.radius_holder)
        radiusTextView = root.findViewById(R.id.radius_text)
        refreshButton = root.findViewById(R.id.refresh)

        //val posts = feedViewModel.postListLiveData.value
        feedViewModel.postListLiveData.observe(
            viewLifecycleOwner,
            Observer { posts ->
                posts?.let {
                    Log.i(TAG, "Got posts ${posts.size}")
                    adapter = PostsAdapter(posts, activity!!, viewLifecycleOwner)
                    feedListView.adapter = adapter
                }
            }
        )
        return root
    }
}