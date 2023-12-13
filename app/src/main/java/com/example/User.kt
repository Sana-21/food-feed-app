package com.example

data class User(
    val username: String,
    val bio: String,
    val profilePictureRes: Int,
    val followers: Int,
    val following: Int,
    val posts: List<Post>
)
