package com.brycecorbitt.artsyapp

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.brycecorbitt.artsyapp.api.Post
import com.brycecorbitt.artsyapp.api.ResponseHandler
import com.brycecorbitt.artsyapp.api.VoteData

private const val TAG = "PostsAdapter"

class PostsAdapter(items: List<Post>, ctx: Context, viewLifecycleOwner: LifecycleOwner) :
        ArrayAdapter<Post>(ctx, R.layout.feed_item, items) {

    private var viewLifecycleOwner = viewLifecycleOwner
    private var appAPI = ResponseHandler(ctx)
    private var ctx = ctx

    private class AttractionItemViewHolder {
        internal var image: ImageView? = null
        internal var votes: TextView? = null
        internal lateinit var upvote: ImageButton
        internal lateinit var downvote: ImageButton
        internal lateinit var hide: ImageButton
        internal lateinit var share: ImageButton
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

            viewHolder.upvote = view.findViewById<View>(R.id.upvote_button) as ImageButton
            viewHolder.downvote = view.findViewById<View>(R.id.downvote_button) as ImageButton
            viewHolder.hide = view.findViewById<View>(R.id.hide_button) as ImageButton
            viewHolder.share = view.findViewById<View>(R.id.share_button) as ImageButton

        } else {
            viewHolder = view.tag as AttractionItemViewHolder
        }

        val post = getItem(i)

        viewHolder.votes!!.text = post!!.score.toString()

        loadVote(post, viewHolder)

        viewHolder.share.setOnClickListener{
            val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Share", "http://mbsr.wpi.edu:6567/post/" + post.id.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context,
                "Post Copied!",
                Toast.LENGTH_SHORT)
                .show()
        }

        viewHolder.upvote!!.setOnClickListener {
            var response = appAPI.upvote(post.id)
            response!!.observe(
                viewLifecycleOwner,
                Observer { voteData ->
                    voteData?.let {
                        handleVote(post, voteData, viewHolder)
                    }
                })
        }

        viewHolder.downvote!!.setOnClickListener {
            var response = appAPI.downvote(post.id)
            response!!.observe(
                viewLifecycleOwner,
                Observer { voteData ->
                    voteData?.let {
                        handleVote(post, voteData, viewHolder)
                    }
                })
        }
        view!!.tag = viewHolder
        val opt = BitmapFactory.Options()
        opt.inJustDecodeBounds = false
        val decodedBytes: ByteArray = Base64.decode(post.content, Base64.NO_WRAP or Base64.URL_SAFE)
        val bmp = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size, opt)
        viewHolder.image?.setImageBitmap(bmp)
        return view
    }

    private fun handleVote(post: Post,voteData: VoteData, viewHolder: AttractionItemViewHolder) {
        viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_default)
        viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_default)

        var voteStatus = voteData.vote_status
        if (voteStatus == Post.VoteStatus.upvoted) {
            viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_pressed)
        }
        else if (voteStatus == Post.VoteStatus.downvoted) {
            viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_pressed)
        }
        post.vote_status = voteStatus
        post.score = voteData.score

        viewHolder.votes!!.text = post!!.score.toString()
    }

    private fun loadVote(post: Post, viewHolder: AttractionItemViewHolder) {
        Log.d(TAG, post.vote_status.toString() + " " + post.score)
        if (post.vote_status == Post.VoteStatus.upvoted){
            viewHolder.upvote.setBackgroundResource(R.drawable.ic_upvote_pressed)
        }
        else if (post.vote_status == Post.VoteStatus.downvoted) {
            viewHolder.downvote.setBackgroundResource(R.drawable.ic_downvote_pressed)
        }
    }
}