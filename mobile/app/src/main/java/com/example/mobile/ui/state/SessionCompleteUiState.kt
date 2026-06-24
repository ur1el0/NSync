package com.example.mobile.ui.state

import com.example.mobile.data.remote.dto.UserProgressDto

data class SessionCompleteUiState(
    val isLoading: Boolean = false,
    val progress: UserProgressDto? = null,
    val error: String? = null
)
