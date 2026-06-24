package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.SessionCompleteUiState
import kotlinx.coroutines.launch

class SessionCompleteViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(SessionCompleteUiState())
        private set

    fun loadProgress() {
        if (uiState.isLoading) return

        viewModelScope.launch {
            uiState = SessionCompleteUiState(isLoading = true)
            try {
                uiState = SessionCompleteUiState(
                    progress = repository.getProgress()
                )
            } catch (e: Exception) {
                uiState = SessionCompleteUiState(
                    error = e.message ?: "Unable to save review results."
                )
            }
        }
    }
}
