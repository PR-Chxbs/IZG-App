package com.prince.izg.data.remote.dto.User

data class UserRequest(
    val id: Int,
    val username: String,
    val first_name: String,
    val second_name: String,
    val gender: String,
    val dob: String,
    val phone_number: String,
    val email: String,
    val password: String = "Test",
    val role: String
)
