package com.example.mobile.data.repository

import com.example.mobile.data.local.AuthSession
import com.example.mobile.data.local.AuthSessionStore
import com.example.mobile.data.remote.RetrofitClient
import com.example.mobile.data.remote.api.ApiService
import com.example.mobile.data.remote.dto.AuthResponseDto
import com.example.mobile.data.remote.dto.LoginRequestDto
import com.example.mobile.data.remote.dto.RefreshRequestDto
import com.example.mobile.data.remote.dto.RegisterRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AuthRepository(
    private val authSessionStore: AuthSessionStore,
    private val apiService: ApiService = RetrofitClient.apiService,
) {
    val sessionFlow: Flow<AuthSession?> = authSessionStore.sessionFlow

    suspend fun register(
        displayName: String,
        email: String,
        password: String,
    ): Result<AuthSession> {
        return try {
            val response = apiService.register(
                RegisterRequestDto(
                    displayName = displayName,
                    email = email,
                    password = password,
                ),
            )

            if (!response.isSuccessful) {
                return Result.failure(
                    IllegalStateException("Registration failed with HTTP ${response.code()}."),
                )
            }

            val authResponse = response.body()
                ?: return Result.failure(
                    IllegalStateException("Registration returned no session data."),
                )

            val session = authResponse.toSession()
            authSessionStore.saveSession(session)

            Result.success(session)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun login(email: String, password: String): Result<AuthSession> {
        return try {
            val response = apiService.login(LoginRequestDto(email, password))

            if (!response.isSuccessful) {
                return Result.failure(
                    IllegalStateException("Login failed with HTTP ${response.code()}."),
                )
            }

            val authResponse = response.body()
                ?: return Result.failure(
                    IllegalStateException("Login returned no session data."),
                )

            val session = authResponse.toSession()
            authSessionStore.saveSession(session)

            Result.success(session)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun refreshSession(): Result<Unit> {
        return try {
            val refreshToken = authSessionStore.refreshTokenFlow.first()
                ?: return Result.failure(
                    IllegalStateException("No refresh token is available."),
                )
            val response = apiService.refreshToken(RefreshRequestDto(refreshToken))

            if (!response.isSuccessful) {
                return Result.failure(
                    IllegalStateException("Token refresh failed with HTTP ${response.code()}."),
                )
            }

            val refreshedTokens = response.body()
                ?: return Result.failure(
                    IllegalStateException("Token refresh returned no token data."),
                )
            authSessionStore.updateTokens(
                accessToken = refreshedTokens.accessToken,
                refreshToken = refreshedTokens.refreshToken,
            )

            Result.success(Unit)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    suspend fun logout(): Result<Unit> {
        return try {
            val refreshToken = authSessionStore.refreshTokenFlow.first()

            if (refreshToken == null) {
                Result.success(Unit)
            } else {
                val response = apiService.logout(RefreshRequestDto(refreshToken))
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(
                        IllegalStateException("Logout failed with HTTP ${response.code()}."),
                    )
                }
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        } finally {
            authSessionStore.clearSession()
        }
    }

    private fun AuthResponseDto.toSession(): AuthSession {
        return AuthSession(
            accessToken = accessToken,
            refreshToken = refreshToken,
            userId = user.id,
            displayName = user.displayName,
            email = user.email,
        )
    }

    suspend fun verifySession(): Result<AuthSession?> {
        return try {
            val session = sessionFlow.first()
                ?: return Result.success(null)
            val response = apiService.getAuthenticatedUser()

            if (!response.isSuccessful) {
                authSessionStore.clearSession()
                return Result.success(null)
            }

            val user = response.body()
                ?: return Result.failure(
                    IllegalStateException("Session verification returned no user data."),
                )
            val verifiedSession = session.copy(
                userId = user.id,
                displayName = user.displayName,
                email = user.email,
            )
            authSessionStore.saveSession(verifiedSession)

            Result.success(verifiedSession)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
