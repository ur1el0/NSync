package com.example.mobile.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.data.SampleData
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.ProgressSummaryCard
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.components.SummaryMetric
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText

@Composable
fun SessionCompleteScreen(
    onDashboardClick: () -> Unit
) {
    val summary = SampleData.sessionSummary

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(NSyncLightBackground)
            .padding(horizontal = 28.dp, vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Text("NSync", color = NSyncBlue, style = ScreenSectionStyle)
            Text("Session Complete", color = ScreenTitle, style = ScreenHeroStyle)
            Text("Nice work — your memory got stronger.", color = NSyncMutedText, style = ScreenBodyStyle)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                SummaryMetric("Score", summary.score, Modifier.weight(1f))
                SummaryMetric("Accuracy", "${summary.accuracyPercent}%", Modifier.weight(1f))
            }
        }

        item {
            ProgressSummaryCard(
                title = "+${summary.xpEarned} XP",
                subtitle = "${summary.streakDays} day streak • ${summary.xpToNextLevel} XP to next level",
                progress = 0.65f
            )
        }

        item {
            PrimaryScreenButton(
                text = "Back to Dashboard",
                onClick = onDashboardClick
            )
        }
    }
}
