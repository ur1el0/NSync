package com.example.mobile.ui.state

import com.example.mobile.data.ReviewCard

data class FlashcardDetailUiState(
    val card: ReviewCard? = null,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val error: String? = null,
    val deleted: Boolean = false
)
