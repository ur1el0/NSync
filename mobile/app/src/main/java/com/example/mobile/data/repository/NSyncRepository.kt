package com.example.mobile.data.repository

import com.example.mobile.data.KnowledgeItem
import com.example.mobile.data.ReviewCard
import com.example.mobile.data.remote.RetrofitClient
import com.example.mobile.data.remote.dto.CreateNoteRequestDto
import com.example.mobile.data.remote.dto.CreateFlashcardRequestDto
import com.example.mobile.data.remote.dto.NoteDto
import com.example.mobile.data.remote.dto.ReviewCompleteRequestDto
import com.example.mobile.data.remote.dto.ReviewCompleteResponseDto
import com.example.mobile.data.remote.dto.UpdateNoteRequestDto
import com.example.mobile.data.remote.dto.UpdateFlashcardRequestDto
import com.example.mobile.data.remote.dto.FlashcardDto
import com.example.mobile.data.remote.dto.UserProgressDto

class NSyncRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun getNotes(): List<NoteDto>? {
        val response = apiService.getNotes()
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getNoteById(id: Int): NoteDto? {
        val response = apiService.getNoteById(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun createNote(title: String, content: String, tag: String): NoteDto? {
        val note = CreateNoteRequestDto(
            title = title,
            content = content,
            tag = tag
        )
        val response = apiService.createNote(note)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun updateNote(id: Int, title: String, content: String, tag: String): NoteDto? {
        val note = UpdateNoteRequestDto(
            title = title,
            content = content,
            tag = tag
        )
        val response = apiService.updateNote(id, note)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun deleteNote(id: Int): Boolean {
        val response = apiService.deleteNote(id)
        return response.isSuccessful
    }

    suspend fun getKnowledgeItemById(id: Int): KnowledgeItem? {
        return getNoteById(id)?.toKnowledgeItem()
    }

    suspend fun getKnowledgeItems(): List<KnowledgeItem> {
        return try {
            getNotes().orEmpty().map { note ->
                note.toKnowledgeItem()
            }
        } catch (e: Exception) {    
            emptyList()
        }
    }

    private fun NoteDto.toKnowledgeItem(): KnowledgeItem {
        return KnowledgeItem(
            id = id,
            title = title,
            collection = tag.ifBlank { "General" },
            source = tag.ifBlank { "Manual note" },
            context = tag.ifBlank { "Knowledge base" },
            summary = content,
            fullNote = content,
            reviewCardCount = 0,
            updatedLabel = "Updated recently",
            masteryPercent = 0,
            xpEarned = 0
        )
    }


    suspend fun getReviewCards(): List<ReviewCard> {
        val response = apiService.getFlashcards()
        if (!response.isSuccessful) {
            throw IllegalStateException("Request failed with HTTP ${response.code()}.")
        }

        return response.body().orEmpty().map { flashcard ->
            flashcard.toReviewCard()
        }
    }

    suspend fun createFlashcard(
        noteId: Int,
        question: String,
        answer: String,
        difficulty: String
    ): ReviewCard? {
        val request = CreateFlashcardRequestDto(
            noteId = noteId,
            question = question,
            answer = answer,
            difficulty = difficulty
        )
        val response = apiService.createFlashcard(request)
        return if (response.isSuccessful) {
            response.body()?.toReviewCard()
        } else {
            null
        }
    }

    suspend fun getReviewCardById(id: Int): ReviewCard? {
        val response = apiService.getFlashcardById(id)
        return if (response.isSuccessful) response.body()?.toReviewCard() else null
    }

    suspend fun updateFlashcard(
        id: Int,
        noteId: Int,
        question: String,
        answer: String,
        difficulty: String,
        masteryLevel: Int
    ): ReviewCard? {
        val request = UpdateFlashcardRequestDto(
            noteId = noteId,
            question = question,
            answer = answer,
            difficulty = difficulty,
            masteryLevel = masteryLevel
        )
        val response = apiService.updateFlashcard(id, request)
        return if (response.isSuccessful) response.body()?.toReviewCard() else null
    }

    suspend fun deleteFlashcard(id: Int): Boolean {
        return apiService.deleteFlashcard(id).isSuccessful
    }

    suspend fun completeReview(
        score: Int,
        totalQuestions: Int,
        xpEarned: Int
    ): ReviewCompleteResponseDto {
        val response = apiService.completeReview(
            ReviewCompleteRequestDto(score, totalQuestions, xpEarned)
        )
        if (!response.isSuccessful) {
            throw IllegalStateException("Unable to save review results (HTTP ${response.code()}).")
        }
        return response.body()
            ?: throw IllegalStateException("The server returned no review result.")
    }

    private fun FlashcardDto.toReviewCard(): ReviewCard {
        return ReviewCard(
            id = id,
            knowledgeItemId = connectedNoteId,
            collection = noteTag.ifBlank { noteTitle.ifBlank { "General" } },
            sourceNoteTitle = noteTitle.ifBlank { "Untitled note" },
            question = question,
            answer = answer,
            difficulty = difficulty.ifBlank { "Unspecified" },
            masteryLabel = masteryLabel(masteryLevel),
            masteryPercent = masteryPercent(masteryLevel),
            updatedLabel = "From knowledge base"
        )
    }

    private fun masteryLabel(level: Int): String {
        return when (level) {
            3 -> "Mastered"
            2 -> "Familiar"
            1 -> "Learning"
            else -> "New"
        }
    }

    private fun masteryPercent(level: Int): Int {
        return when (level) {
            3 -> 100
            2 -> 66
            1 -> 33
            else -> 0
        }
    }

    suspend fun getProgress(): UserProgressDto {
        val response = apiService.getProgress()
        if (!response.isSuccessful) {
            throw IllegalStateException("Unable to load progress (HTTP ${response.code()}).")
        }
        return response.body()
            ?: throw IllegalStateException("The server returned no progress data.")
    }
}
