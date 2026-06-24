package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RegisterRequestDto(
    @SerializedName("display_name") val displayName: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class LoginRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class AuthenticatedUserDto(
    @SerializedName("id") val id: Long,
    @SerializedName("email") val email: String,
    @SerializedName("display_name") val displayName: String,
)

data class AuthResponseDto(
    @SerializedName("user") val user: AuthenticatedUserDto,
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String
)

data class RefreshRequestDto(
    @SerializedName("refresh") val refreshToken: String,
)

data class RefreshResponseDto(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String,
)
