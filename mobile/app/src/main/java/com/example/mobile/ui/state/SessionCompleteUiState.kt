package com.example.mobile.ui.state

import com.example.mobile.data.remote.dto.ReviewCompleteResponseDto

data class SessionCompleteUiState(
    val isSaving: Boolean = false,
    val result: ReviewCompleteResponseDto? = null,
    val error: String? = null
)
