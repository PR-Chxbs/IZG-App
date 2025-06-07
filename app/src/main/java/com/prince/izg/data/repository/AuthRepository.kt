package com.prince.izg.data.repository

import com.prince.izg.data.remote.api.AuthApi
import com.prince.izg.data.remote.dto.Auth.LoginRequest
import com.prince.izg.data.remote.dto.Auth.LoginResponse
import com.prince.izg.data.remote.dto.Auth.RegisterRequest
import com.prince.izg.data.remote.dto.Auth.RegisterResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {

    suspend fun registerUser(registerRequest: RegisterRequest): Response<RegisterResponse> {
        return authApi.register(registerRequest)
    }

    suspend fun loginUser(loginRequest: LoginRequest): Response<LoginResponse> {
        return authApi.login(loginRequest)
    }
}
