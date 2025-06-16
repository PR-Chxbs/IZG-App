package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.Post.PostRequest
import com.prince.izg.data.remote.dto.Post.PostResponse
import retrofit2.Response
import retrofit2.http.*

interface PostApi {

    @POST("posts")
    suspend fun addPost(
        @Header("Authorization") token: String,
        @Body post: PostRequest
    ): Response<PostResponse>

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse>>

    @GET("posts/{id}")
    suspend fun getPostById(
        @Path("id") id: Int
    ): Response<PostResponse>

    @PUT("posts/{id}")
    suspend fun updatePostById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body post: PostRequest
    ): Response<PostResponse>

    @DELETE("posts/{id}")
    suspend fun deletePostById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<PostResponse>

    @PUT("posts/publish/{id}")
    suspend fun publishPostById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<PostResponse>

    @PUT("posts/unpublish/{id}")
    suspend fun unpublishPostById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<PostResponse>
}
