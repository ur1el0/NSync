package com.example.mobile.data.remote.interceptor

import com.example.mobile.data.local.AuthSessionStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val authSessionStore: AuthSessionStore,
) : Interceptor {
    private val publicPaths = setOf(
        "/api/health/",
        "/api/auth/register/",
        "/api/auth/login/",
        "/api/auth/refresh/",
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (originalRequest.url.encodedPath in publicPaths) {
            return chain.proceed(originalRequest)
        }

        val accessToken = runBlocking {
            authSessionStore.accessTokenFlow.first()
        }
        if (accessToken.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(authenticatedRequest)
    }
}
