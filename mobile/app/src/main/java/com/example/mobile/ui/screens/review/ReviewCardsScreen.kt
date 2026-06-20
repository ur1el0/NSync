package com.example.mobile.ui.screens.review

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.mobile.data.SampleData
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.ReviewCardListItem

@Composable
fun ReviewCardsScreen(
    onStartReviewClick: () -> Unit,
    onRouteClick: (String) -> Unit
) {
    MainScreenScaffold(
        currentRoute = Routes.REVIEW_CARDS,
        title = "Review Cards",
        subtitle = "Due today",
        onRouteClick = onRouteClick
    ) {
        item {
            PrimaryScreenButton(
                text = "Start Review",
                onClick = onStartReviewClick
            )
        }

        items(SampleData.reviewCards) { card ->
            ReviewCardListItem(card)
        }
    }
}
