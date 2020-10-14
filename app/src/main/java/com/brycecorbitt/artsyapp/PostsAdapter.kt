package com.brycecorbitt.artsyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.*

class PostsAdapter(items: ArrayList<PostTemp>, ctx: Context) :
        ArrayAdapter<PostTemp>(ctx, R.layout.feed_item, items) {

    private class AttractionItemViewHolder {
        internal var image: ImageView? = null
        internal var votes: TextView? = null
        internal lateinit var upvote: Button
        internal lateinit var downvote: Button
        internal lateinit var hide: Button
        internal lateinit var share: Button
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        val viewHolder: AttractionItemViewHolder

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.feed_item, viewGroup, false)

            viewHolder = AttractionItemViewHolder()
            viewHolder.votes = view.findViewById<View>(R.id.net_votes_text) as TextView
            viewHolder.image = view.findViewById<View>(R.id.post_image) as ImageView

            viewHolder.upvote = view.findViewById<View>(R.id.upvote_button) as Button
            viewHolder.downvote = view.findViewById<View>(R.id.downvote_button) as Button
            viewHolder.hide = view.findViewById<View>(R.id.hide_button) as Button
            viewHolder.share = view.findViewById<View>(R.id.share_button) as Button

        } else {
            viewHolder = view.tag as AttractionItemViewHolder
        }

        val post = getItem(i)
        val voteHandler = VoteHandler(post, viewHolder)
        voteHandler.loadVote()
        viewHolder.votes!!.text = post!!.net.toString()

        viewHolder.upvote!!.setOnClickListener {
            //TODO: hookup database
            voteHandler.handleNewVote(UPVOTED)
        }

        viewHolder.downvote!!.setOnClickListener {
            //TODO: hookup database
            voteHandler.handleNewVote(DOWNVOTED)
        }
        view!!.tag = viewHolder

        return view
    }

    /*
    TODO: REFACTOR FOR API
    - each setOnClickListener should make get request to API
    - Callback will be a JSON object with new net votes and new vote status
    - Callback can call generic function for up and downvote
        - reset backgrounds
        - set net upvote
        - set vote status
        - use vote status to change appropriate vote button
    - VoteHandler class can be removed
     */

    private inner class VoteHandler(post: PostTemp?, viewHolder: AttractionItemViewHolder) {
        private val viewHolder = viewHolder
        private val post = post

        fun upVote(newVote: Int) {
            if (post!!.voteStatus == newVote) {
                post.net -= 1
                post.voteStatus = UNVOTED
            } else if (post.voteStatus == DOWNVOTED) {
                post!!.net += 2
                post.voteStatus = UPVOTED
                viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_pressed)
            } else if (post.voteStatus == UNVOTED) {
                post!!.net += 1
                post.voteStatus = UPVOTED
                viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_pressed)
            }
        }

        fun downVote(newVote: Int) {
            if (post!!.voteStatus == newVote) {
                post.net += 1
                post.voteStatus = UNVOTED
            } else if (post.voteStatus == UPVOTED) {
                post!!.net -= 2
                post.voteStatus = DOWNVOTED
                viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_pressed)
            } else if (post.voteStatus == UNVOTED) {
                post!!.net -= 1
                post.voteStatus = DOWNVOTED
                viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_pressed)
            }
        }

        fun loadVote() {
            if (post!!.voteStatus == UPVOTED)
                viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_pressed)
            else if (post.voteStatus == DOWNVOTED)
                viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_pressed)
        }

        fun handleNewVote(newVote: Int) {
            viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_default)
            viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_default)

            if (newVote == UPVOTED) upVote(newVote)
            else if (newVote == DOWNVOTED) downVote(newVote)

            viewHolder.votes!!.text = post!!.net.toString()
        }
    }
}