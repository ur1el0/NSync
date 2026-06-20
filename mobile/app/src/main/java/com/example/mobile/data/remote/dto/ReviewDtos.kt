package com.example.mobile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReviewCompleteRequestDto(
    @SerializedName("score") val score: Int,
    @SerializedName("total_questions") val totalQuestions: Int,
    @SerializedName("xp_earned") val xpEarned: Int
)

data class QuizAttemptDto(
    @SerializedName("id") val id: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("total_questions") val totalQuestions: Int,
    @SerializedName("xp_earned") val xpEarned: Int,
    @SerializedName("date_taken") val dateTaken: String
)

data class UserProgressDto(
    @SerializedName("id") val id: Int,
    @SerializedName("total_xp") val totalXp: Int,
    @SerializedName("level") val level: Int,
    @SerializedName("streak") val streak: Int,
    @SerializedName("total_reviews") val totalReviews: Int,
    @SerializedName("correct_reviews") val correctReviews: Int,
    @SerializedName("accuracy") val accuracy: Double
)

data class ReviewCompleteResponseDto(
    @SerializedName("attempt") val attempt: QuizAttemptDto,
    @SerializedName("progress") val progress: UserProgressDto
)
