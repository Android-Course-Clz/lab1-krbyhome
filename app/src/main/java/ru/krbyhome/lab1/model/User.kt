package ru.krbyhome.lab1.model

data class User(
    val avatarUrl: String,
    val name: String,
    val username: String,
    val followers: Int,
    val following: Int,
    val posts: Int,
    var isFollowing: Boolean
)