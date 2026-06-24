package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.ReviewCard
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.data.remote.dto.ReviewAnswerDto
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

    var isCompleting by mutableStateOf(false)
        private set

    var completedResult by mutableStateOf<ReviewSessionResult?>(null)
        private set

    private val answers = mutableListOf<ReviewAnswerDto>()

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
            completedResult = null
            isCompleting = false
            answers.clear()
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

    fun recordAnswer(recalled: Boolean) {
        val card = currentCard ?: return
        if (isCompleting) return

        answers += ReviewAnswerDto(flashcardId = card.id, recalled = recalled)
        if (recalled) correctAnswers += 1

        if (hasNextCard) {
            nextCard()
            return
        }

        completeSession()
    }

    fun consumeCompletedResult() {
        completedResult = null
    }

    private fun completeSession() {
        viewModelScope.launch {
            isCompleting = true
            error = null
            try {
                val response = repository.completeReview(answers.toList())
                completedResult = ReviewSessionResult(
                    score = response.attempt.score,
                    totalQuestions = response.attempt.totalQuestions,
                    xpEarned = response.attempt.xpEarned
                )
            } catch (e: Exception) {
                error = e.message ?: "Unable to save review results."
            } finally {
                isCompleting = false
            }
        }
    }
}
