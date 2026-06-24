package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.AuthRepository
import com.example.mobile.ui.state.AuthUiState
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
) : ViewModel() {
    var uiState by mutableStateOf(AuthUiState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            repository.login(email, password)
                .onSuccess { session ->
                    uiState = uiState.copy(isLoading = false, session = session)
                }
                .onFailure { error ->
                    uiState = uiState.copy(isLoading = false, errorMessage = error.message)
                }
        }
    }

    fun register(displayName: String, email: String, password: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            repository.register(displayName, email, password)
                .onSuccess { session ->
                    uiState = uiState.copy(isLoading = false, session = session)
                }
                .onFailure { error ->
                    uiState = uiState.copy(isLoading = false, errorMessage = error.message)
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            repository.logout()
                .onFailure { error ->
                    uiState = uiState.copy(errorMessage = error.message)
                }
            uiState = uiState.copy(isLoading = false, session = null)
        }
    }

    fun restoreSession() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            repository.verifySession()
                .onSuccess { session ->
                    uiState = uiState.copy(
                        isLoading = false,
                        session = session,
                        hasRestoredSession = true,
                    )
                }
                .onFailure { error ->
                    uiState = uiState.copy(
                        isLoading = false,
                        errorMessage = error.message,
                        hasRestoredSession = true,
                    )
                }
        }
    }

    fun clearError() {
        uiState = uiState.copy(errorMessage = null)
    }
}
