package com.example.mobile.ui.state

import com.example.mobile.data.CollectionMastery
import com.example.mobile.data.remote.dto.UserProgressDto

data class MasteryUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val progress: UserProgressDto? = null,
    val masteryGroups: List<CollectionMastery> = emptyList()
)
