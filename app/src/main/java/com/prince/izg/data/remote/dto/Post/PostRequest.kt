package com.prince.izg.data.remote.dto.Post

data class PostRequest(
    val author_id: Int,
    val title: String,
    val slug: String,
    val content: String,
    val cover_image: String? = null,
    val published: Boolean = false,
    val published_at: String? = null
)