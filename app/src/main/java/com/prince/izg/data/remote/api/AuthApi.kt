package com.prince.izg.data.remote.api

import com.prince.izg.data.remote.dto.Auth.LoginRequest
import com.prince.izg.data.remote.dto.Auth.RegisterRequest
import com.prince.izg.data.remote.dto.Auth.LoginResponse
import com.prince.izg.data.remote.dto.Auth.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthApi {

    @POST("/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>
}
