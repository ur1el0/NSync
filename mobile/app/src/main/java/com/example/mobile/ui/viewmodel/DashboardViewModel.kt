package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.DashboardUiState
import kotlinx.coroutines.launch


class DashboardViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(DashboardUiState())
        private set

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            uiState = DashboardUiState(isLoading = true)
            try {
                val progress = repository.getProgress()
                val items = repository.getKnowledgeItems()
                uiState = DashboardUiState(
                    progress = progress,
                    recentKnowledge = items
                )
            } catch (e: Exception) {
                uiState = DashboardUiState(
                    error = e.message ?: "Unable to load dashboard."
                )
            }
        }
    }
}
