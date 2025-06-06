package com.prince.izg.data.remote.dto.Auth

data class RegisterRequest(
    val username: String,
    val first_name: String,
    val second_name: String,
    val gender: String,
    val dob: String,
    val phone_number: String,
    val email: String,
    val password: String
)
