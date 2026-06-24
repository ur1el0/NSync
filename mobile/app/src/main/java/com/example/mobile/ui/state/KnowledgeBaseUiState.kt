package com.example.mobile.ui.state

import com.example.mobile.data.KnowledgeItem

data class KnowledgeBaseUiState(
    val items: List<KnowledgeItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
