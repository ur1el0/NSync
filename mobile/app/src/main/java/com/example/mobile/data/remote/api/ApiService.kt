package com.example.mobile.data.remote.api

import com.example.mobile.data.remote.dto.CreateNoteRequestDto
import com.example.mobile.data.remote.dto.CreateFlashcardRequestDto
import com.example.mobile.data.remote.dto.FlashcardDto
import com.example.mobile.data.remote.dto.NoteDto
import com.example.mobile.data.remote.dto.ReviewCompleteRequestDto
import com.example.mobile.data.remote.dto.ReviewCompleteResponseDto
import com.example.mobile.data.remote.dto.UpdateNoteRequestDto
import com.example.mobile.data.remote.dto.UpdateFlashcardRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Path

interface ApiService {
    // Notes API endpoints
    // Get notes
    @GET("/api/notes/")
    suspend fun getNotes(): Response<List<NoteDto>>
    @GET("/api/notes/{id}/")
    suspend fun getNoteById(@Path("id") id: Int): Response<NoteDto>

    // Create note
    @POST("/api/notes/")
    suspend fun createNote(@Body note: CreateNoteRequestDto): Response<NoteDto>

    @PUT("/api/notes/{id}/")
    suspend fun updateNote(
        @Path("id") id: Int,
        @Body note: UpdateNoteRequestDto
    ): Response<NoteDto>

    // Delete note
    @DELETE("/api/notes/{id}/")
    suspend fun deleteNote(@Path("id") id: Int): Response<Unit>


    // Flashcards API endpoints
    // Get flashcards
    @GET("/api/flashcards/")
    suspend fun getFlashcards(): Response<List<FlashcardDto>>
    // Get flashcard by ID
    @GET("/api/flashcards/{id}/")
    suspend fun getFlashcardById(@Path("id") id: Int): Response<FlashcardDto>

    @POST("/api/flashcards/")
    suspend fun createFlashcard(
        @Body flashcard: CreateFlashcardRequestDto
    ): Response<FlashcardDto>

    @PUT("/api/flashcards/{id}/")
    suspend fun updateFlashcard(
        @Path("id") id: Int,
        @Body flashcard: UpdateFlashcardRequestDto
    ): Response<FlashcardDto>

    @DELETE("/api/flashcards/{id}/")
    suspend fun deleteFlashcard(@Path("id") id: Int): Response<Unit>

    @POST("/api/review/complete/")
    suspend fun completeReview(
        @Body review: ReviewCompleteRequestDto
    ): Response<ReviewCompleteResponseDto>
}
