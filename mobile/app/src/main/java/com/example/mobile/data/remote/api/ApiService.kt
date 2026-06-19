package com.example.mobile.data.remote.api

import com.example.mobile.data.remote.dto.NoteDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.DELETE
import retrofit2.http.Patch
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    // Notes API endpoints
    // Get notes
    @GET("/api/notes/")
    suspend fun getNotes(): Response<NoteDto>

    @GET("/api/notes/{id}/")
    suspend fun getNoteById(@Path("id") id: Int): Response<NoteDto>


    // Create note
    @POST("/api/notes/")
    suspend fun createNote(@Body note: NoteDto): Response<NoteDto>

    // Update note
    @PUT("/api/notes/{id}/")
    suspend fun updateNote(@Path("id") id: Int, @Body note: NoteDto): Response<NoteDto>

    // Partial update note
    @Patch("/api/notes/{id}/")
    suspend fun partialUpdateNote(@Path("id") id: Int, @Body note: NoteDto): Response<NoteDto>

    // Delete note
    @DELETE("/api/notes/{id}/")
    suspend fun deleteNote(@Path("id") id: Int): Response<Unit>
    
}