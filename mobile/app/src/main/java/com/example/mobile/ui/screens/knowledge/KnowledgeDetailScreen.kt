package com.example.mobile.ui.screens.knowledge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.data.SampleData
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.ProgressSummaryCard
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.theme.NSyncMutedText

@Composable
fun KnowledgeDetailScreen(
    onStartReviewClick: () -> Unit,
    onRouteClick: (String) -> Unit
) {
    val item = SampleData.detailKnowledge

    MainScreenScaffold(
        currentRoute = Routes.KNOWLEDGE_BASE,
        title = "Knowledge Detail",
        subtitle = item.collection,
        onRouteClick = onRouteClick
    ) {
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
