package com.prince.izg.data.remote.dto.User

data class UserResponse(
    val id: Int,
    val username: String,
    val first_name: String,
    val second_name: String,
    val gender: String,
    val dob: String,
    val age: String?,
    val phone_number: String,
    val email: String,
    val role: String,
    val created_at: String
)