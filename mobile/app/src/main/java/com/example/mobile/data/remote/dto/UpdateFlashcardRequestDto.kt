package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdateFlashcardRequestDto(
    @SerializedName("connected_note") val noteId: Int,
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("mastery_level") val masteryLevel: Int
)
