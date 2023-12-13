package com.example

data class Restaurant(
    val name: String,
    val rating: Int,
    val location: String,
    val contactInfo: String,
    val menuItems: List<MenuItem>,
    val posts: List<Post> // Assuming you have a Post data class defined
)
