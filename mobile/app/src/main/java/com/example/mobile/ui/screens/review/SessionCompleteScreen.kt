package com.example.mobile.ui.screens.review

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.data.SampleData
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.BottomNavBar
import com.example.mobile.ui.theme.InterFontFamily
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenTitle

@Composable
fun SessionCompleteScreen(
    onDashboardClick: () -> Unit,
    onReviewAgainClick: () -> Unit,
    onRouteClick: (String) -> Unit
) {
    val summary = SampleData.sessionSummary

    Scaffold(
        containerColor = NSyncLightBackground,
        bottomBar = {
            BottomNavBar(
                currentRoute = Routes.DASHBOARD,
                onRouteClick = onRouteClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            SessionCompleteTopBar(onBackClick = onDashboardClick)

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE4EEFF)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(NSyncBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✓", color = Color.White, style = SessionCheckStyle)
                }
            }

            Text(
                text = "Session Complete",
                color = ScreenTitle,
                style = SessionTitleStyle,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Nice work - your memory got stronger.",
                color = NSyncMutedText,
                style = SessionBodyStyle,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SessionMetricCard("Score", summary.score, Modifier.weight(1f))
                SessionMetricCard("Accuracy", "${summary.accuracyPercent}%", Modifier.weight(1f))
            }

            XpEarnedCard(xpEarned = summary.xpEarned)

            StreakCard(streakDays = summary.streakDays)

            LevelProgressSection(xpToNextLevel = summary.xpToNextLevel)

            Button(
                onClick = onReviewAgainClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NSyncBlue)
            ) {
                Text("↻ Review Again", style = SessionButtonStyle)
            }

            OutlinedButton(
                onClick = onDashboardClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                border = BorderStroke(1.dp, Color(0xFF9AA3B4)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = NSyncBlue)
            ) {
                Text("▦ Back to Dashboard", style = SessionOutlineButtonStyle)
            }
        }
    }
}

@Composable
private fun SessionCompleteTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "‹",
                color = NSyncBlue,
                style = SessionBackStyle,
                modifier = Modifier.clickable(onClick = onBackClick)
            )
            Text("NSync", color = NSyncBlue, style = SessionLogoStyle)
        }
        Image(
            painter = painterResource(id = R.drawable.roosc),
            contentDescription = "User profile",
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
private fun SessionMetricCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(84.dp),
        colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFE8ECF4))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(label.uppercase(), color = NSyncMutedText, style = SessionTinyStyle)
            Text(value, color = NSyncBlue, style = SessionMetricValueStyle)
        }
    }
}

@Composable
private fun XpEarnedCard(xpEarned: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(74.dp),
        colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFE8ECF4))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF7F8DA3)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✦", color = Color.White, style = SessionTinyStyle)
                }
                Column {
                    Text("XP earned", color = NSyncMutedText, style = SessionTinyStyle)
                    Text("+$xpEarned XP", color = ScreenTitle, style = SessionSmallBoldStyle)
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(999.dp))
                    .background(NSyncBlue)
                    .padding(horizontal = 9.dp, vertical = 4.dp)
            ) {
                Text("BOOSTED", color = Color.White, style = SessionBadgeStyle)
            }
        }
    }
}

@Composable
private fun StreakCard(streakDays: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEDEFF6)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("●", color = Color(0xFFFF6B00), style = SessionMetricValueStyle)
                Column {
                    Text("$streakDays day streak", color = ScreenTitle, style = SessionSmallBoldStyle)
                    Text("Keep reviewing to maintain momentum.", color = NSyncMutedText, style = SessionTinyStyle)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(7.dp)) {
                repeat(8) { index ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(999.dp))
                            .background(if (index < 5) NSyncBlue else Color(0xFFBFD7F3))
                    )
                }
            }
        }
    }
}

@Composable
private fun LevelProgressSection(xpToNextLevel: Int) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Level", color = NSyncMutedText, style = SessionTinyStyle)
            Text("$xpToNextLevel XP to Level 6", color = NSyncBlue, style = SessionTinyBoldBlueStyle)
        }
        LinearProgressIndicator(
            progress = { 0.82f },
            modifier = Modifier
                .fillMaxWidth()
                .height(9.dp)
                .clip(RoundedCornerShape(999.dp)),
            color = NSyncBlue,
            trackColor = Color(0xFFDCE2EC)
        )
        Text(
            text = "Level progress updated",
            modifier = Modifier.fillMaxWidth(),
            color = NSyncMutedText,
            style = SessionTinyItalicStyle,
            textAlign = TextAlign.Center
        )
    }
}

private val SessionLogoStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight.Bold
)

private val SessionBackStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
)

private val SessionCheckStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 20.sp,
    fontWeight = FontWeight.Black
)

private val SessionTitleStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 28.sp,
    fontWeight = FontWeight.Bold
)

private val SessionBodyStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 13.sp,
    fontWeight = FontWeight.Medium
)

private val SessionTinyStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 10.sp,
    fontWeight = FontWeight.Bold
)

private val SessionTinyBoldBlueStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 9.sp,
    fontWeight = FontWeight.Bold
)

private val SessionTinyItalicStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 9.sp,
    fontWeight = FontWeight.Bold
)

private val SessionMetricValueStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 23.sp,
    fontWeight = FontWeight.Bold
)

private val SessionSmallBoldStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 13.sp,
    fontWeight = FontWeight.Bold
)

private val SessionBadgeStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 8.sp,
    fontWeight = FontWeight.Black
)

private val SessionButtonStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    fontWeight = FontWeight.Bold
)

private val SessionOutlineButtonStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 13.sp,
    fontWeight = FontWeight.Bold
)
