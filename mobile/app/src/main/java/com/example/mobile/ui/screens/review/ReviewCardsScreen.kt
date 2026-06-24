package com.example.mobile.ui.screens.review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.ReviewSessionListItem
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.viewmodel.ReviewCardsViewModel

@Composable
fun ReviewCardsScreen(
    onStartSessionClick: (Int) -> Unit,
    onAddCardClick: (Int) -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: ReviewCardsViewModel = viewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, viewModel) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadCards()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

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
                ReviewSessionListItem(
                    title = noteTitle,
                    tag = tag,
                    cardCount = cards.size,
                    onStartSessionClick = { onStartSessionClick(noteId) },
                    onAddCardClick = { onAddCardClick(noteId) }
                )
            }
        }
    }
}
