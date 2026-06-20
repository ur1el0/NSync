package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.ReviewCard
import com.example.mobile.data.repository.NSyncRepository
import kotlinx.coroutines.launch

class ReviewCardsViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var cards by mutableStateOf<List<ReviewCard>>(emptyList())
        private set

    init {
        loadCards()
    }

    fun loadCards() {
        viewModelScope.launch {
            isLoading = true
            error = null

            try {
                cards = repository.getReviewCards()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
