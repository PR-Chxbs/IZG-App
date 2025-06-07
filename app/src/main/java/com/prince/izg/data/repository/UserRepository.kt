package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.UserApi
import com.prince.izg.data.remote.dto.User.UserRequest
import com.prince.izg.data.remote.dto.User.UserResponse
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApi: UserApi
) {
    suspend fun addUser(token: String, user: UserRequest): Response<UserResponse> {
        return userApi.addUser("Bearer $token", user)
    }

    suspend fun getUsers(token: String): Response<List<UserResponse>> {
        return userApi.getUsers("Bearer $token")
    }

    suspend fun getUserById(token: String, id: Int): Response<UserResponse> {
        return userApi.getUserById("Bearer $token", id)
    }

    suspend fun updateUserById(token: String, id: Int, user: UserRequest): Response<UserResponse> {
        return userApi.updateUserById("Bearer $token", id, user)
    }

    suspend fun deleteUserById(token: String, id: Int): Response<UserResponse> {
        return userApi.deleteUserById("Bearer $token", id)
    }
}
