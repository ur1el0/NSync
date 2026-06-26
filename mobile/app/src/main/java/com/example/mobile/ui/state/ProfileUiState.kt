package com.example.mobile.ui.state

import com.example.mobile.data.remote.dto.UserProgressDto

data class ProfileUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val displayName: String = "User",
    val email: String = "",
    val learningGoal: String = "Ready to strengthen what matters today?",
    val progress: UserProgressDto? = null
)
