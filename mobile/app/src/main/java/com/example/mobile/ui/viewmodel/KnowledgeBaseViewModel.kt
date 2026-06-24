package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.KnowledgeBaseUiState
import kotlinx.coroutines.launch


class KnowledgeBaseViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(KnowledgeBaseUiState())
        private set

    init {
        loadNotes()
    }

    fun loadNotes() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val items = repository.getKnowledgeItems()
                uiState = uiState.copy(items = items, isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message, isLoading = false)
            }
        }
    }
}
