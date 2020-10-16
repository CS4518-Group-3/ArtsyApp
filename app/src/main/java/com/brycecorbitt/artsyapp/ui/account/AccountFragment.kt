package com.brycecorbitt.artsyapp.ui.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.brycecorbitt.artsyapp.PostsAdapter
import com.brycecorbitt.artsyapp.R
import com.brycecorbitt.artsyapp.api.User

import com.squareup.picasso.Picasso

class AccountFragment : Fragment() {

    private lateinit var accountViewModel: AccountViewModel
    private lateinit var textViewName: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var profilePicture: ImageView
    private lateinit var user: User
    private lateinit var feedListView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = User.get()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        accountViewModel =
                ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        //val textView: TextView = root.findViewById(R.id.text_account)


        textViewName = root.findViewById(R.id.tv_name)
        textViewEmail = root.findViewById(R.id.tv_email)
        profilePicture = root.findViewById(R.id.imageView_profile)
        feedListView = root.findViewById(R.id.account_list_view) as ListView

        Picasso.get().load(user.profile_url).into(profilePicture)


        textViewName.text = user.name
        textViewEmail.text = user.email

        accountViewModel.listLiveData.observe(
            viewLifecycleOwner,
            Observer { posts ->
                posts?.let {
                    Log.i("Account", "Got posts ${posts.size}")
                    val  postsAdapter = PostsAdapter(posts, activity!!)
                    feedListView.adapter = postsAdapter
                }
            }
        )
        return root
    }

    override fun onStart() {
        super.onStart()

    }
}