package com.example.mobile.data.repository

import com.example.mobile.data.KnowledgeItem
import com.example.mobile.data.remote.RetrofitClient
import com.example.mobile.data.remote.dto.CreateNoteRequestDto
import com.example.mobile.data.remote.dto.NoteDto
import com.example.mobile.data.remote.dto.UpdateNoteRequestDto

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
}
