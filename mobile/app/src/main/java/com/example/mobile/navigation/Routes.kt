package com.example.mobile.navigation

import com.example.mobile.R

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val KNOWLEDGE_BASE = "knowledge_base"
    const val KNOWLEDGE_DETAIL = "knowledge_detail/{noteId}"
    const val NEW_NOTE = "new_note"
    const val EDIT_NOTE = "edit_note/{noteId}"
    const val NEW_FLASHCARD = "new_flashcard/{noteId}"
    const val FLASHCARD_DETAIL = "flashcard_detail/{cardId}"
    const val EDIT_FLASHCARD = "edit_flashcard/{cardId}"
    const val REVIEW_SESSION_NOTE = "review_session/note/{noteId}"
    const val REVIEW_CARDS = "review_cards"
    const val REVIEW_SESSION = "review_session"
    const val SESSION_COMPLETE = "session_complete/{score}/{totalQuestions}/{xpEarned}"
    const val MASTERY = "mastery"
    const val PROFILE = "profile"

    fun knowledgeDetail(noteId: Int): String = "knowledge_detail/$noteId"

    fun editNote(noteId: Int): String = "edit_note/$noteId"

    fun newFlashcard(noteId: Int): String = "new_flashcard/$noteId"

    fun flashcardDetail(cardId: Int): String = "flashcard_detail/$cardId"

    fun editFlashcard(cardId: Int): String = "edit_flashcard/$cardId"

    fun reviewSessionForNote(noteId: Int): String = "review_session/note/$noteId"

    fun sessionComplete(score: Int, totalQuestions: Int, xpEarned: Int): String =
        "session_complete/$score/$totalQuestions/$xpEarned"
}

data class BottomNavRoute(
    val label: String,
    val route: String,
    val iconRes: Int
)

val bottomNavRoutes = listOf(
    BottomNavRoute(
        label = "Dashboard",
        route = Routes.DASHBOARD,
        iconRes = R.drawable.ic_grid
    ),
    BottomNavRoute(
        label = "Knowledge",
        route = Routes.KNOWLEDGE_BASE,
        iconRes = R.drawable.ic_briefcase
    ),
    BottomNavRoute(
        label = "Flashcards",
        route = Routes.REVIEW_CARDS,
        iconRes = R.drawable.ic_flashcard
    ),
    BottomNavRoute(
        label = "Mastery",
        route = Routes.MASTERY,
        iconRes = R.drawable.ic_chart
    ),
    BottomNavRoute(
        label = "Profile",
        route = Routes.PROFILE,
        iconRes = R.drawable.ic_profile
    )
)
