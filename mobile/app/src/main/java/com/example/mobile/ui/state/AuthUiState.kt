package com.example.mobile.ui.state

import com.example.mobile.data.local.AuthSession

data class AuthUiState(
    val isLoading: Boolean = false,
    val session: AuthSession? = null,
    val errorMessage: String? = null,
    val hasRestoredSession: Boolean = false,
)