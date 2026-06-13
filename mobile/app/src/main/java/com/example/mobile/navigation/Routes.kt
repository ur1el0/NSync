package com.example.mobile.navigation

import com.example.mobile.R

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val KNOWLEDGE_BASE = "knowledge_base"
    const val KNOWLEDGE_DETAIL = "knowledge_detail"
    const val NEW_NOTE = "new_note"
    const val REVIEW_CARDS = "review_cards"
    const val REVIEW_SESSION = "review_session"
    const val SESSION_COMPLETE = "session_complete"
    const val MASTERY = "mastery"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
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
