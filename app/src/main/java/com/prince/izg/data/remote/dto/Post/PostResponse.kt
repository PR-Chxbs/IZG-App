package com.prince.izg.data.remote.dto.Post

data class PostResponse(
    val id: Int,
    val author_id: Int,
    val first_name: String?,
    val title: String,
    val slug: String,
    val content: String,
    val cover_image: String?,
    val published: Boolean,
    val published_at: String?,
    val created_at: String,
    val updated_at: String
)
