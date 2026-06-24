package com.example.mobile.data

data class UserProfile(
    val name: String,
    val email: String,
    val learningGoal: String,
    val level: Int,
    val totalXp: Int,
    val xpToNextLevel: Int,
    val streakDays: Int,
    val accuracyPercent: Int
)

data class KnowledgeItem(
    val id: Int,
    val title: String,
    val collection: String,
    val source: String,
    val context: String,
    val summary: String,
    val fullNote: String,
    val reviewCardCount: Int,
    val updatedLabel: String,
    val masteryPercent: Int,
    val xpEarned: Int
)

data class ReviewCard(
    val id: Int,
    val knowledgeItemId: Int,
    val collection: String,
    val question: String,
    val answer: String,
    val difficulty: String,
    val masteryLabel: String,
    val masteryPercent: Int,
    val updatedLabel: String
)

data class CollectionMastery(
    val collection: String,
    val context: String,
    val masteryPercent: Int
)

data class RecentAttempt(
    val title: String,
    val timeLabel: String,
    val score: String,
    val rank: String
)

data class ReviewSessionSummary(
    val score: String,
    val accuracyPercent: Int,
    val xpEarned: Int,
    val streakDays: Int,
    val levelLabel: String,
    val xpToNextLevel: Int
)
