package ru.krbyhome.lab1.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.krbyhome.lab1.R
import ru.krbyhome.lab1.model.Post

class PostAdapter(
    private val onLikeClick: (Post) -> Unit,
    private val onCommentClick: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback()) {

    companion object {
        private const val BIND_TAG = "PostAdapterBind"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postText: TextView = itemView.findViewById(R.id.postText)
        private val postImage: ImageView = itemView.findViewById(R.id.postImage)
        private val likeButton: Button = itemView.findViewById(R.id.likeButton)
        private val commentButton: Button = itemView.findViewById(R.id.commentButton)
        private val userAvatar: ImageView = itemView.findViewById(R.id.userAvatar)
        private val userName: TextView = itemView.findViewById(R.id.userName)
        private val userNickname: TextView = itemView.findViewById(R.id.userNickname)

        fun bind(post: Post) {
            bindPostText(post)
            bindUserInfo(post)
            bindPostImage(post)
            bindLikeButton(post)
            bindCommentButton(post)

            Log.i(BIND_TAG, "Loading post: $post")
        }

        private fun bindPostText(post: Post) {
            postText.text = post.text
        }

        private fun bindUserInfo(post: Post) {
            Glide.with(itemView.context)
                .load(post.userAvatar)
                .circleCrop()
                .into(userAvatar)

            userName.text = post.userName
            userNickname.text = post.userNickname
        }

        private fun bindPostImage(post: Post) {
            if (post.imageUrl != null) {
                postImage.visibility = View.VISIBLE
                Glide.with(itemView)
                    .load(post.imageUrl)
                    .centerCrop()
                    .into(postImage)
            } else {
                postImage.visibility = View.GONE
            }
        }

        private fun bindLikeButton(post: Post) {
            likeButton.text = post.likes.toString()
            likeButton.setOnClickListener { onLikeClick(post) }
        }

        private fun bindCommentButton(post: Post) {
            commentButton.text = post.comments.toString()
            commentButton.setOnClickListener { onCommentClick(post) }
        }
    }
}