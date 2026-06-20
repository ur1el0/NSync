package com.example.mobile.ui.screens.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenSmallBoldStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.viewmodel.ReviewCardsViewModel

@Composable
fun ReviewCardsScreen(
    onStartSessionClick: (Int) -> Unit,
    onAddCardClick: (Int) -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: ReviewCardsViewModel = viewModel()
) {
    MainScreenScaffold(
        currentRoute = Routes.REVIEW_CARDS,
        title = "Review Sessions",
        subtitle = "Organized by knowledge note",
        onRouteClick = onRouteClick
    ) {
        if (viewModel.isLoading) {
            item {
                Text("Loading review cards...", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        viewModel.error?.let { message ->
            item {
                Text("Unable to load cards: $message", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        if (!viewModel.isLoading && viewModel.error == null && viewModel.cards.isEmpty()) {
            item {
                Text("No review cards saved yet.", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        val cardsBySession = viewModel.cards.groupBy { card ->
            Triple(card.knowledgeItemId, card.sourceNoteTitle, card.collection)
        }

        cardsBySession.forEach { (session, cards) ->
            val (noteId, noteTitle, tag) = session
            item(key = "session-$noteId") {
                Text(noteTitle, color = ScreenTitle, style = ScreenSectionStyle)
                Text(
                    text = "$tag - ${cards.size} card${if (cards.size == 1) "" else "s"}",
                    color = NSyncMutedText,
                    style = ScreenSmallBoldStyle
                )
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TextButton(onClick = { onStartSessionClick(noteId) }) {
                        Text("Start session", color = NSyncBlue, style = ScreenSmallBoldStyle)
                    }
                    TextButton(onClick = { onAddCardClick(noteId) }) {
                        Text("Add card", color = NSyncBlue, style = ScreenSmallBoldStyle)
                    }
                }
            }
        }
    }
}
