package com.example.mobile.ui.screens.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.data.SampleData
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.ProgressSummaryCard
import com.example.mobile.ui.components.SummaryMetric


@Composable
fun MasteryScreen(onRouteClick: (String) -> Unit) {
    val user = SampleData.userProfile
    MainScreenScaffold(
        currentRoute = Routes.MASTERY,
        title = "Mastery",
        subtitle = "Track progress by collection",
        onRouteClick = onRouteClick
    ) {
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SummaryMetric("TOTAL XP", user.totalXp.toString(), Modifier.weight(1f))
                SummaryMetric("LEVEL", user.level.toString(), Modifier.weight(1f))
            }
        }

        items(SampleData.collectionMastery) { mastery ->
            ProgressSummaryCard(
                title = mastery.collection,
                subtitle = mastery.context,
                progress = mastery.masteryPercent / 100f,
                trailing = "${mastery.masteryPercent}%"
            )
        }
    }
}
