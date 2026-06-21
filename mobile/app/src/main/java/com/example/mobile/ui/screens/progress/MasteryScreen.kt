package com.example.mobile.ui.screens.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.ProgressSummaryCard
import com.example.mobile.ui.components.SummaryMetric
import com.example.mobile.ui.viewmodel.MasteryViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle

@Composable
fun MasteryScreen(onRouteClick: (String) -> Unit) {
    val uiState = viewModel<MasteryViewModel>().uiState
    val progress = uiState.progress
    val totalXp = progress?.totalXp ?: 0
    val totalReviews = progress?.totalReviews ?: 0

    MainScreenScaffold(
        currentRoute = Routes.MASTERY,
        title = "Mastery",
        subtitle = "Track progress by collection",
        onRouteClick = onRouteClick
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SummaryMetric("TOTAL XP", totalXp.toString(), Modifier.weight(1f))
                SummaryMetric("TOTAL REVIEWS", totalReviews.toString(), Modifier.weight(1f))
            }
        }

        if (uiState.isLoading) {
            item {
                Text("Loading mastery...", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        uiState.error?.let { message ->
            item {
                Text("Unable to load mastery: $message", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        if (!uiState.isLoading && uiState.error == null && uiState.masteryGroups.isEmpty()) {
            item {
                Text("No review cards saved yet.", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }

        items(uiState.masteryGroups) { mastery ->
            ProgressSummaryCard(
                title = mastery.collection,
                subtitle = mastery.context,
                progress = mastery.masteryPercent / 100f,
                trailing = "${mastery.masteryPercent}%"
            )
        }
    }
}
