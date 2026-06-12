package com.example.mobile.navigation

import com.example.mobile.navigation.AppNavigation
import com.example.mobile.ui.screens.DashboardScreen

object Routes {
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val DASHBOARD = "dashboard"
    const val KNOWLEDGE_BASE = "knowledge_base"
    const val KNOWLEDGE_DETAIL = "knowledge_detail"
    const val REVIEW_CARDS = "review_cards"
    const val REVIEW_SESSION = "review_session"
    const val SESSION_COMPLETE = "session_complete"
    const val MASTERY = "mastery"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
}

data class BottomNavRoute(
    val label: String,
    val route: String
)

val bottomNavRoutes = listOf(
    BottomNavRoute(
        label = "Dashboard",
        route = Routes.DASHBOARD
    ),
    BottomNavRoute(
        label = "Notes",
        route = Routes.KNOWLEDGE_BASE
    ),
    BottomNavRoute(
        label = "Cards",
        route = Routes.REVIEW_CARDS
    ),
    BottomNavRoute(
        label = "Progress",
        route = Routes.MASTERY
    ),
    BottomNavRoute(
        label = "Profile",
        route = Routes.PROFILE
    )
)
