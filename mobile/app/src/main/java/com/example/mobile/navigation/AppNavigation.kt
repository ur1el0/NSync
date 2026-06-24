package com.example.mobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile.ui.screens.auth.LoginScreen
import com.example.mobile.ui.screens.auth.RegisterScreen
import com.example.mobile.ui.screens.dashboard.DashboardScreen
import com.example.mobile.ui.screens.knowledge.KnowledgeBaseScreen
import com.example.mobile.ui.screens.knowledge.KnowledgeDetailScreen
import com.example.mobile.ui.screens.knowledge.NewNoteScreen
import com.example.mobile.ui.screens.profile.ProfileScreen
import com.example.mobile.ui.screens.progress.MasteryScreen
import com.example.mobile.ui.screens.review.ReviewCardsScreen
import com.example.mobile.ui.screens.review.ReviewSessionScreen
import com.example.mobile.ui.screens.review.SessionCompleteScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navigateBottom: (String) -> Unit = { route ->
        if (navController.currentDestination?.route != route) {
            if (route == Routes.DASHBOARD) {
                val popped = navController.popBackStack(Routes.DASHBOARD, inclusive = false)
                if (!popped) {
                    navController.navigate(Routes.DASHBOARD) {
                        launchSingleTop = true
                    }
                }
            } else {
                navController.navigate(route) {
                    launchSingleTop = true
                    popUpTo(Routes.DASHBOARD) {
                        saveState = true
                    }
                    restoreState = true
                }
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onRegisterClick = {
                    navController.navigate(Routes.REGISTER)
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onRegisterClick = { _, _, _ ->
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                    }
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.DASHBOARD) {
            DashboardScreen(
                onStartReviewClick = { navController.navigate(Routes.REVIEW_SESSION) },
                onKnowledgeClick = { navController.navigate(Routes.KNOWLEDGE_DETAIL) },
                onAddClick = { navController.navigate(Routes.REVIEW_CARDS) },
                onRouteClick = navigateBottom
            )
        }

        composable(Routes.KNOWLEDGE_BASE) {
            KnowledgeBaseScreen(
                onKnowledgeClick = { navController.navigate(Routes.KNOWLEDGE_DETAIL) },
                onNewNoteClick = { navController.navigate(Routes.NEW_NOTE) },
                onRouteClick = navigateBottom
            )
        }

        composable(Routes.NEW_NOTE) {
            NewNoteScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.popBackStack(Routes.KNOWLEDGE_BASE, inclusive = false) },
                onRouteClick = navigateBottom
            )
        }

        composable(Routes.KNOWLEDGE_DETAIL) {
            KnowledgeDetailScreen(
                onStartReviewClick = { navController.navigate(Routes.REVIEW_SESSION) },
                onRouteClick = navigateBottom
            )
        }

        composable(Routes.REVIEW_CARDS) {
            ReviewCardsScreen(
                onStartReviewClick = { navController.navigate(Routes.REVIEW_SESSION) },
                onRouteClick = navigateBottom
            )
        }

        composable(Routes.REVIEW_SESSION) {
            ReviewSessionScreen(
                onCompleteClick = { navController.navigate(Routes.SESSION_COMPLETE) }
            )
        }

        composable(Routes.MASTERY) {
            MasteryScreen(onRouteClick = navigateBottom)
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onRouteClick = navigateBottom,
                onLogoutClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Routes.SESSION_COMPLETE) {
            SessionCompleteScreen(
                onRouteClick = navigateBottom,
                onReviewAgainClick = { navController.navigate(Routes.REVIEW_SESSION) },
                onDashboardClick = {
                    navController.navigate(Routes.DASHBOARD) {
                        popUpTo(Routes.DASHBOARD) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
