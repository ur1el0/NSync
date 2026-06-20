package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.ReviewCard
import com.example.mobile.data.repository.NSyncRepository
import kotlinx.coroutines.launch

class ReviewSessionViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var cards by mutableStateOf<List<ReviewCard>>(emptyList())
        private set

    var currentIndex by mutableStateOf(0)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    val currentCard: ReviewCard?
        get() = cards.getOrNull(currentIndex)

    val hasNextCard: Boolean
        get() = currentIndex < cards.lastIndex

    fun loadSession(cardId: Int? = null) {
        viewModelScope.launch {
            isLoading = true
            error = null
            currentIndex = 0
            try {
                val reviewCards = repository.getReviewCards()
                cards = if (cardId == null) {
                    reviewCards
                } else {
                    reviewCards.filter { it.id == cardId }
                }
            } catch (e: Exception) {
                cards = emptyList()
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    fun nextCard() {
        if (hasNextCard) currentIndex += 1
    }
}
