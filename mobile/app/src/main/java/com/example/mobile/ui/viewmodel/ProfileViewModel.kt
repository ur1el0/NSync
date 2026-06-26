package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.local.AuthSession
import com.example.mobile.data.remote.RetrofitClient
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.ProfileUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            val session = RetrofitClient.sessionStore.sessionFlow.first()
            uiState = ProfileUiState(
                isLoading = true,
                displayName = session.profileName(),
                email = session?.email.orEmpty()
            )
            try {
                val progress = repository.getProgress()
                uiState = ProfileUiState(
                    displayName = session.profileName(),
                    email = session?.email.orEmpty(),
                    progress = progress
                )
            } catch (e: Exception) {
                uiState = ProfileUiState(
                    displayName = session.profileName(),
                    email = session?.email.orEmpty(),
                    error = e.message ?: "Unable to load profile."
                )
            }
        }
    }

    private fun AuthSession?.profileName(): String {
        return this?.displayName
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: this?.email
                ?.substringBefore("@")
                ?.takeIf { it.isNotEmpty() }
            ?: "User"
    }
}
