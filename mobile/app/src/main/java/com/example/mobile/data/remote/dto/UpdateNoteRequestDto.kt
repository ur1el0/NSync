package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UpdateNoteRequestDto(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("tag") val tag: String
)
