package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.ReviewCard
import com.example.mobile.data.repository.NSyncRepository
import kotlinx.coroutines.launch

class NewFlashcardViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var isSaving by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var saved by mutableStateOf(false)
        private set

    var loadedCard by mutableStateOf<ReviewCard?>(null)
        private set

    fun saveFlashcard(noteId: Int, question: String, answer: String, difficulty: String) {
        if (question.isBlank() || answer.isBlank()) {
            error = "Question and answer are required."
            return
        }

        viewModelScope.launch {
            isSaving = true
            error = null
            saved = false

            val card = repository.createFlashcard(noteId, question, answer, difficulty)
            if (card != null) {
                saved = true
            } else {
                error = "Unable to save review card."
            }

            isSaving = false
        }
    }

    fun loadCard(cardId: Int) {
        if (loadedCard?.id == cardId) return

        viewModelScope.launch {
            loadedCard = repository.getReviewCardById(cardId)
            if (loadedCard == null) error = "Unable to load review card."
        }
    }

    fun updateFlashcard(card: ReviewCard, question: String, answer: String, difficulty: String) {
        if (question.isBlank() || answer.isBlank()) {
            error = "Question and answer are required."
            return
        }

        viewModelScope.launch {
            isSaving = true
            error = null
            saved = false

            val updated = repository.updateFlashcard(
                id = card.id,
                noteId = card.knowledgeItemId,
                question = question,
                answer = answer,
                difficulty = difficulty,
                masteryLevel = when (card.masteryLabel) {
                    "Mastered" -> 3
                    "Familiar" -> 2
                    "Learning" -> 1
                    else -> 0
                }
            )
            if (updated != null) saved = true else error = "Unable to update review card."
            isSaving = false
        }
    }
}
