package com.brycecorbitt.artsyapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
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
    private var page: Int = 1
    private var limit: Int = 10

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
            //TODO:
            // change browsing mode & image
            // refresh feed with new browsing mode (update location TextView)
        }

        //TODO: check/update location TextView

        radiusLayout.setOnClickListener {
            val navController = findNavController(activity!!, R.id.nav_host_fragment)
            navController.navigate(R.id.navigation_preferences)
        }

        radiusTextView.text = preferencesViewModel.currentRadius.toInt().toString()

        refreshButton.setOnClickListener {
            //TODO:
            // refresh feed
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
                    val  postsAdapter = PostsAdapter(posts, activity!!)
                    feedListView.adapter = postsAdapter
                }
            }
        )
        return root
    }
}