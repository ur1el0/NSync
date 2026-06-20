package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.ReviewCard
import com.example.mobile.data.repository.NSyncRepository
import kotlinx.coroutines.launch

data class ReviewSessionResult(
    val score: Int,
    val totalQuestions: Int,
    val xpEarned: Int
)

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

    var correctAnswers by mutableStateOf(0)
        private set

    val currentCard: ReviewCard?
        get() = cards.getOrNull(currentIndex)

    val hasNextCard: Boolean
        get() = currentIndex < cards.lastIndex

    fun loadSession(noteId: Int? = null, cardId: Int? = null) {
        viewModelScope.launch {
            isLoading = true
            error = null
            currentIndex = 0
            correctAnswers = 0
            try {
                val reviewCards = repository.getReviewCards()
                cards = when {
                    noteId != null -> reviewCards.filter { it.knowledgeItemId == noteId }
                    cardId != null -> reviewCards.filter { it.id == cardId }
                    else -> reviewCards
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

    fun recordAnswer(recalled: Boolean): ReviewSessionResult? {
        if (currentCard == null) return null
        if (recalled) correctAnswers += 1

        if (hasNextCard) {
            nextCard()
            return null
        }

        return ReviewSessionResult(
            score = correctAnswers,
            totalQuestions = cards.size,
            xpEarned = cards.size * XP_PER_CARD
        )
    }

    private companion object {
        const val XP_PER_CARD = 25
    }
}
