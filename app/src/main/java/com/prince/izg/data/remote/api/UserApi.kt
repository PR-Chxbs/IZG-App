package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.User.UserRequest
import com.prince.izg.data.remote.dto.User.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {

    @POST("users")
    suspend fun addUser(
        @Header("Authorization") token: String,
        @Body user: UserRequest
    ): Response<UserResponse>

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String
    ): Response<List<UserResponse>>

    @GET("users/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<UserResponse>

    @PUT("users/{id}")
    suspend fun updateUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body user: UserRequest
    ): Response<UserResponse>

    @DELETE("users/{id}")
    suspend fun deleteUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<UserResponse>
}