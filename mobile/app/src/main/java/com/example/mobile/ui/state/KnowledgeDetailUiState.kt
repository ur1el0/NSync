package com.example.mobile.ui.state

import com.example.mobile.data.KnowledgeItem

data class KnowledgeDetailUiState(
    val item: KnowledgeItem? = null,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val error: String? = null,
    val deleted: Boolean = false
)
