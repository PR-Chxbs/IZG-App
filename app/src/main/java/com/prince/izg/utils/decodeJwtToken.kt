package com.prince.izg.utils

import com.auth0.android.jwt.JWT

fun decodeJwtToken(token: String): Map<String, String?> {
    val jwt = JWT(token)
    return mapOf(
        "userId" to jwt.getClaim("userId").asString(),
        "role" to jwt.getClaim("role").asString(),
        "email" to jwt.getClaim("email").asString()
        // Add more as needed
    )
}
