package com.example.mobile.ui.state

import com.example.mobile.data.remote.dto.UserProgressDto

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val progress: UserProgressDto? = null
)
