package com.example.mobile.data.remote

import android.content.Context
import com.example.mobile.data.local.AuthSessionStore
import com.example.mobile.data.remote.api.ApiService
import com.example.mobile.data.remote.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://127.0.0.1:8000/"

    private lateinit var initializedSessionStore: AuthSessionStore
    private lateinit var initializedApiService: ApiService

    @Synchronized
    fun initialize(context: Context) {
        if (::initializedApiService.isInitialized) {
            return
        }

        initializedSessionStore = AuthSessionStore(context.applicationContext)
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(initializedSessionStore))
            .build()

        initializedApiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    val sessionStore: AuthSessionStore
        get() {
            check(::initializedSessionStore.isInitialized) {
                "RetrofitClient.initialize must be called before accessing the session store."
            }
            return initializedSessionStore
        }

    val apiService: ApiService
        get() {
            check(::initializedApiService.isInitialized) {
                "RetrofitClient.initialize must be called before accessing the API service."
            }
            return initializedApiService
        }
}
