package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FlashcardDto(
    @SerializedName("id") val id: Int,
    @SerializedName("connected_note") val connectedNoteId: Int,
    @SerializedName("question") val question: String,
    @SerializedName("answer") val answer: String,
    @SerializedName("note_title") val noteTitle: String,
    @SerializedName("note_tag") val noteTag: String,
    @SerializedName("difficulty") val difficulty: String,
    @SerializedName("mastery_level") val masteryLevel: Int
)
