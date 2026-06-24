package com.example.mobile.ui.screens.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.ProgressSummaryCard
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenButtonStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.viewmodel.FlashcardDetailViewModel

@Composable
fun FlashcardDetailScreen(
    cardId: Int,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onStartSessionClick: (Int) -> Unit,
    onDeleted: () -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: FlashcardDetailViewModel = viewModel()
) {
    val uiState = viewModel.uiState
    val card = uiState.card

    LaunchedEffect(cardId) { viewModel.loadCard(cardId) }
    LaunchedEffect(uiState.deleted) { if (uiState.deleted) onDeleted() }

    MainScreenScaffold(
        currentRoute = Routes.REVIEW_CARDS,
        title = "Review Card",
        subtitle = card?.collection ?: "Loading card...",
        onRouteClick = onRouteClick
    ) {
        if (uiState.isLoading) {
            item { Text("Loading review card...", color = NSyncMutedText, style = ScreenBodyStyle) }
        }
        uiState.error?.let { message ->
            item { Text(message, color = Color(0xFFD21F2B), style = ScreenBodyStyle) }
        }
        if (card == null && !uiState.isLoading) {
            item { PrimaryScreenButton("Back to Review Cards", onBackClick) }
            return@MainScreenScaffold
        }
        card ?: return@MainScreenScaffold

        item { Text(card.question, color = ScreenTitle, style = ScreenHeroStyle) }
        item {
            Text("Answer", color = ScreenTitle, style = ScreenSectionStyle)
            Text(card.answer, color = NSyncMutedText, style = ScreenBodyStyle)
        }
        item {
            ProgressSummaryCard(
                title = card.masteryLabel,
                subtitle = "${card.difficulty} - ${card.masteryPercent}% mastery",
                progress = card.masteryPercent / 100f
            )
        }
        item {
            PrimaryScreenButton(
                text = "Start Note Session",
                onClick = { onStartSessionClick(card.knowledgeItemId) }
            )
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = onEditClick, modifier = Modifier.weight(1f)) {
                    Text("Edit", color = NSyncBlue, style = ScreenButtonStyle)
                }
                OutlinedButton(onClick = { viewModel.deleteCard(cardId) }, modifier = Modifier.weight(1f)) {
                    Text(
                        if (uiState.isDeleting) "Deleting..." else "Delete",
                        color = Color(0xFFD21F2B),
                        style = ScreenButtonStyle
                    )
                }
            }
        }
    }
}
