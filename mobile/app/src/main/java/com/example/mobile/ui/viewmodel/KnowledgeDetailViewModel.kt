package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.KnowledgeDetailUiState
import kotlinx.coroutines.launch

class KnowledgeDetailViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(KnowledgeDetailUiState())
        private set

    fun loadNote(id: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null, deleted = false)
            try {
                val item = repository.getKnowledgeItemById(id)
                uiState = if (item != null) {
                    uiState.copy(item = item, isLoading = false)
                } else {
                    uiState.copy(error = "Note not found.", isLoading = false)
                }
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message, isLoading = false)
            }
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            uiState = uiState.copy(isDeleting = true, error = null)
            try {
                val deleted = repository.deleteNote(id)
                uiState = if (deleted) {
                    uiState.copy(isDeleting = false, deleted = true)
                } else {
                    uiState.copy(isDeleting = false, error = "Unable to delete note.")
                }
            } catch (e: Exception) {
                uiState = uiState.copy(isDeleting = false, error = e.message)
            }
        }
    }
}
