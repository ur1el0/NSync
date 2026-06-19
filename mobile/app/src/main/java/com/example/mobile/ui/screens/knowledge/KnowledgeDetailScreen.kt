package com.example.mobile.ui.screens.knowledge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.ProgressSummaryCard
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenButtonStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.viewmodel.KnowledgeDetailViewModel

@Composable
fun KnowledgeDetailScreen(
    noteId: Int,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleted: () -> Unit,
    onStartReviewClick: () -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: KnowledgeDetailViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val item = uiState.item

    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    LaunchedEffect(uiState.deleted) {
        if (uiState.deleted) {
            onDeleted()
        }
    }

    MainScreenScaffold(
        currentRoute = Routes.KNOWLEDGE_BASE,
        title = "Knowledge Detail",
        subtitle = item?.collection ?: "Loading note...",
        onRouteClick = onRouteClick
    ) {
        if (uiState.isLoading) {
            item {
                Text("Loading note...", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        uiState.error?.let { message ->
            item {
                Text(message, color = Color(0xFFD21F2B), style = ScreenBodyStyle)
            }
        }

        if (item == null && !uiState.isLoading) {
            item {
                PrimaryScreenButton(
                    text = "Back to Knowledge Base",
                    onClick = onBackClick
                )
            }
            return@MainScreenScaffold
        }

        item ?: return@MainScreenScaffold

        item {
            Text(
                text = item.title,
                color = ScreenTitle,
                style = ScreenHeroStyle
            )
        }

        item {
            ProgressSummaryCard(
                title = "${item.masteryPercent}% Mastered",
                subtitle = "${item.reviewCardCount} review cards • +${item.xpEarned} XP earned",
                progress = item.masteryPercent / 100f
            )
        }

        item {
            PrimaryScreenButton(
                text = "Start Review",
                onClick = onStartReviewClick
            )
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = onEditClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Edit", color = NSyncBlue, style = ScreenButtonStyle)
                }
                OutlinedButton(
                    onClick = { viewModel.deleteNote(noteId) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        if (uiState.isDeleting) "Deleting..." else "Delete",
                        color = Color(0xFFD21F2B),
                        style = ScreenButtonStyle
                    )
                }
            }
        }

        item {
            Text("Full Note", color = ScreenTitle, style = ScreenSectionStyle)
            Text(
                text = item.fullNote,
                modifier = Modifier.padding(top = 10.dp),
                color = NSyncMutedText,
                style = ScreenBodyStyle
            )
        }
    }
}
