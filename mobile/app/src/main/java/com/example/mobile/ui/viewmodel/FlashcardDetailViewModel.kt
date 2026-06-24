package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.FlashcardDetailUiState
import kotlinx.coroutines.launch

class FlashcardDetailViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(FlashcardDetailUiState())
        private set

    fun loadCard(id: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null, deleted = false)
            try {
                val card = repository.getReviewCardById(id)
                uiState = if (card != null) {
                    uiState.copy(card = card, isLoading = false)
                } else {
                    uiState.copy(error = "Review card not found.", isLoading = false)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun deleteCard(id: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isDeleting = true, error = null)
            try {
                val deleted = repository.deleteFlashcard(id)
                uiState = if (deleted) {
                    uiState.copy(isDeleting = false, deleted = true)
                } else {
                    uiState.copy(isDeleting = false, error = "Unable to delete review card.")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isDeleting = false, error = e.message)
            }
        }
    }
}
