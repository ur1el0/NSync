package com.example.mobile.data.local

data class AuthSession(
    val accessToken: String,
    val refreshToken: String,
    val userId: Long,
    val displayName: String,
    val email: String,
)
