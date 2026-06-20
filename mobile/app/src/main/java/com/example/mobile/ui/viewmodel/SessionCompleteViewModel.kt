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

    fun saveReview(score: Int, totalQuestions: Int, xpEarned: Int) {
        if (uiState.isSaving || uiState.result != null) return

        viewModelScope.launch {
            uiState = uiState.copy(isSaving = true, error = null)
            try {
                uiState = SessionCompleteUiState(
                    result = repository.completeReview(score, totalQuestions, xpEarned)
                )
            } catch (e: Exception) {
                uiState = SessionCompleteUiState(
                    error = e.message ?: "Unable to save review results."
                )
            }
        }
    }
}
