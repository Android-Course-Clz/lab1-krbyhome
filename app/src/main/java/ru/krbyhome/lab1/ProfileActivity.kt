package ru.krbyhome.lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.krbyhome.lab1.adapter.PostAdapter
import ru.krbyhome.lab1.model.Post
import ru.krbyhome.lab1.model.User

class ProfileActivity : AppCompatActivity() {
    private lateinit var adapter: PostAdapter
    private val user = createDemoUser()
    private val demoPosts = createDemoPosts()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setupUserProfile()
        setupButtons()
        setupRecyclerView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupUserProfile() {
        Glide.with(this)
            .load(user.avatarUrl)
            .circleCrop()
            .into(findViewById(R.id.avatar))

        findViewById<TextView>(R.id.userName).text = user.name
        findViewById<TextView>(R.id.userNickname).text = user.username
        findViewById<TextView>(R.id.followersCount).text = "${user.followers}\nподписчиков"
        findViewById<TextView>(R.id.followingCount).text = "${user.following}\nподписок"
        findViewById<TextView>(R.id.postsCount).text = "${user.posts}\nпостов"
    }

    private fun setupButtons() {
        val followBtn = findViewById<Button>(R.id.followButton)
        followBtn.setOnClickListener {
            user.isFollowing = !user.isFollowing
            followBtn.text = if (user.isFollowing) "Отписаться" else "Подписаться"
        }

        findViewById<Button>(R.id.messageButton).setOnClickListener {
            showToast("Написать сообщение")
        }
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter(
            onLikeClick = { post ->
                post.isLiked = !post.isLiked
                post.likes += if (post.isLiked) 1 else -1
                adapter.notifyItemChanged(demoPosts.indexOf(post))
            },
            onCommentClick = { post ->
                showToast("Комментарии (${post.comments})")
            }
        )

        with(findViewById<RecyclerView>(R.id.postsRecycler)) {
            layoutManager = LinearLayoutManager(this@ProfileActivity)
            adapter = this@ProfileActivity.adapter
        }

        adapter.submitList(demoPosts)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun createDemoUser(): User = User(
        avatarUrl = "https://isbucket.storage.yandexcloud.net/864c3a78-1c5c-44e0-b608-2656300f64fe-avatar-production",
        name = "Егор Суфияров",
        username = "@krbyhome",
        followers = 372,
        following = 87,
        posts = 5,
        isFollowing = false
    )

    private fun createDemoPosts(): List<Post> = listOf(
        Post(1, "и настроения", null, 16, 0, false, user.avatarUrl, user.name, user.username),
        Post(2, "Всем хорошего дня!", "https://clck.ru/3MCcgt", 33, 7, false, user.avatarUrl, user.name, user.username),
        Post(3, "привет", null, 5, 1, false, user.avatarUrl, user.name, user.username),
        Post(4, "Еще пару постов для того чтобы потестить скролл :)", "https://clck.ru/3MCcw4", 213, 43, false, user.avatarUrl, user.name, user.username),
        Post(5, "Первый пост", null, 12, 3, false, user.avatarUrl, user.name, user.username),
    )
}