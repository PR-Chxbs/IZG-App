package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.PostApi
import com.prince.izg.data.remote.dto.Post.PostRequest
import com.prince.izg.data.remote.dto.Post.PostResponse
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postApi: PostApi
) {

    suspend fun addPost(token: String, post: PostRequest): PostResponse {
        return postApi.addPost("Bearer $token", post)
    }

    suspend fun getPosts(): List<PostResponse> {
        return postApi.getPosts()
    }

    suspend fun getPostById(token: String, id: Int): PostResponse {
        return postApi.getPostById(id)
    }

    suspend fun updatePost(token: String, id: Int, post: PostRequest): PostResponse {
        return postApi.updatePostById("Bearer $token", id, post)
    }

    suspend fun deletePost(token: String, id: Int): PostResponse {
        return postApi.deletePostById("Bearer $token", id)
    }

    suspend fun publishPost(token: String, id: Int): PostResponse {
        return postApi.publishPostById("Bearer $token", id)
    }

    suspend fun unpublishPost(token: String, id: Int): PostResponse {
        return postApi.unpublishPostById("Bearer $token", id)
    }
}
