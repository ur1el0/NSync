package com.example.mobile.ui.screens.review

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.ReviewCardListItem
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.viewmodel.ReviewCardsViewModel

@Composable
fun ReviewCardsScreen(
    onStartReviewClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: ReviewCardsViewModel = viewModel()
) {
    MainScreenScaffold(
        currentRoute = Routes.REVIEW_CARDS,
        title = "Review Cards",
        subtitle = "Due today",
        onRouteClick = onRouteClick
    ) {
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

        items(viewModel.cards) { card ->
            ReviewCardListItem(card, onClick = { onCardClick(card.id) })
        }
    }
}
