package com.example.mobile.ui.state

import com.example.mobile.data.KnowledgeItem
import com.example.mobile.data.remote.dto.UserProgressDto

data class DashboardUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val progress: UserProgressDto? = null,
    val recentKnowledge: List<KnowledgeItem> = emptyList()
)