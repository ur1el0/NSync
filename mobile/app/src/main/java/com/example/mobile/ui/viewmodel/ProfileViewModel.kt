package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.ProfileUiState
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(ProfileUiState())
        private set

    init {
        loadProfile()
    }

    fun loadProfile() {
        viewModelScope.launch {
            uiState = ProfileUiState(isLoading = true)
            try {
                val progress = repository.getProgress()
                uiState = ProfileUiState(
                    progress = progress
                )
            } catch (e: Exception) {
                uiState = ProfileUiState(
                    error = e.message ?: "Unable to load profile."
                )
            }
        }
    }
}