package com.example.mobile.ui.screens.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile.R
import com.example.mobile.data.KnowledgeItem
import com.example.mobile.data.SampleData
import com.example.mobile.data.UserProfile
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.BottomNavBar
import com.example.mobile.ui.theme.InterFontFamily
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.NSyncRed

@Composable
fun DashboardScreen(
    onStartReviewClick: () -> Unit = {},
    onKnowledgeClick: (KnowledgeItem) -> Unit = {},
    onAddClick: () -> Unit = {},
    onRouteClick: (String) -> Unit = {}
) {
    val user = SampleData.userProfile
    val recentKnowledge = SampleData.dashboardRecentKnowledge

    Scaffold(
        containerColor = NSyncLightBackground,
        bottomBar = {
            BottomNavBar(
                currentRoute = Routes.DASHBOARD,
                onRouteClick = onRouteClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
                DashboardTopNav()
            }

            item {
                DashboardGreeting(user.name)
            }

            item {
                LevelProgressCard(user)
            }

            item {
                DashboardStatsRow(user.streakDays, user.accuracyPercent)
            }

            item {
                DashboardActions(
                    onStartReviewClick = onStartReviewClick,
                    onAddClick = onAddClick
                )
            }

            item {
                RecentKnowledgeSection(
                    recentKnowledge = recentKnowledge,
                    onKnowledgeClick = onKnowledgeClick
                )
            }

            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
private fun DashboardTopNav() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "NSync",
            color = NSyncBlue,
            style = DashboardLogoStyle
        )

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F1FF))
                .border(2.dp, NSyncBlue, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nsync_logo),
                contentDescription = "User profile",
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
private fun DashboardGreeting(name: String) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "Hello, $name!",
            color = DashboardTitle,
            style = DashboardHeadlineStyle
        )
        Text(
            text = "Ready to strengthen what matters today?",
            color = NSyncMutedText,
            style = DashboardBodyStyle
        )
    }
}

@Composable
private fun LevelProgressCard(user: UserProfile) {
    val totalForLevel = user.totalXp + user.xpToNextLevel
    val progress = user.totalXp.toFloat() / totalForLevel.toFloat()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = DashboardCardBorder
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "LEVEL",
                color = NSyncMutedText,
                style = DashboardMicroLabelStyle
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "Level ${user.level}",
                    color = NSyncBlue,
                    style = DashboardLevelStyle
                )
                Text(
                    text = "${formatNumber(user.totalXp)} XP • ${user.xpToNextLevel} to Level ${user.level + 1}",
                    color = NSyncMutedText,
                    style = DashboardSmallBoldStyle
                )
            }

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(999.dp)),
                color = Color(0xFF7D889A),
                trackColor = Color(0xFFE2E5EA)
            )
        }
    }
}

@Composable
private fun DashboardStatsRow(streakDays: Int, accuracyPercent: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_wind,
            iconTint = NSyncRed,
            value = streakDays.toString(),
            label = "Day streak"
        )

        StatCard(
            modifier = Modifier.weight(1f),
            icon = R.drawable.ic_target,
            iconTint = NSyncBlue,
            value = "$accuracyPercent%",
            label = "Accuracy"
        )
    }
}

@Composable
private fun StatCard(
    modifier: Modifier,
    icon: Int,
    iconTint: Color,
    value: String,
    label: String
) {
    Card(
        modifier = modifier.height(84.dp),
        colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = DashboardCardBorder
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = iconTint
            )
            Text(
                text = value,
                color = DashboardTitle,
                style = DashboardStatValueStyle
            )
            Text(
                text = label,
                color = NSyncMutedText,
                style = DashboardMicroBoldStyle
            )
        }
    }
}

@Composable
private fun DashboardActions(
    onStartReviewClick: () -> Unit,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .height(48.dp)
                .shadow(10.dp, RoundedCornerShape(999.dp))
                .clickable(onClick = onStartReviewClick),
            color = NSyncBlue,
            shape = RoundedCornerShape(999.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "▶ Start Review",
                    color = Color.White,
                    style = DashboardActionStyle
                )
            }
        }

        Surface(
            modifier = Modifier
                .size(48.dp)
                .clickable(onClick = onAddClick),
            color = Color.Transparent,
            shape = CircleShape,
            border = BorderStroke(2.dp, NSyncBlue)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "+",
                    color = NSyncBlue,
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
private fun RecentKnowledgeSection(
    recentKnowledge: KnowledgeItem,
    onKnowledgeClick: (KnowledgeItem) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Recent Knowledge",
            color = DashboardTitle,
            style = DashboardSectionTitleStyle
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onKnowledgeClick(recentKnowledge) },
            colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            border = DashboardCardBorder
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFF0F3F8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_briefcase),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp),
                        tint = NSyncMutedText
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = recentKnowledge.title,
                        color = DashboardTitle,
                        style = DashboardKnowledgeTitleStyle,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = recentKnowledge.collection,
                        color = NSyncMutedText,
                        style = DashboardBodyStyle
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        InfoPill("${recentKnowledge.reviewCardCount} Cards")
                        InfoPill("+${recentKnowledge.xpEarned} XP")
                    }
                }

                Text(
                    text = "›",
                    color = Color(0xFFC8CEDA),
                    style = TextStyle(
                        fontFamily = InterFontFamily,
                        fontSize = 28.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Normal
                    )
                )
            }
        }
    }
}

@Composable
private fun InfoPill(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(Color(0xFFE8ECF3))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = text,
            color = NSyncMutedText,
            style = DashboardPillStyle
        )
    }
}

private fun formatNumber(value: Int): String = "%,d".format(value)

private val DashboardTitle = Color(0xFF151927)
private val DashboardCardBorder = BorderStroke(1.dp, Color(0xFFE9EDF5))

private val DashboardLogoStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 21.sp,
    lineHeight = 26.sp,
    fontWeight = FontWeight.ExtraBold
)

private val DashboardHeadlineStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 28.sp,
    lineHeight = 34.sp,
    fontWeight = FontWeight.ExtraBold
)

private val DashboardBodyStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    lineHeight = 18.sp,
    fontWeight = FontWeight.Normal
)

private val DashboardMicroLabelStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 9.sp,
    lineHeight = 12.sp,
    fontWeight = FontWeight.ExtraBold,
    letterSpacing = 0.7.sp
)

private val DashboardSmallBoldStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 11.sp,
    lineHeight = 14.sp,
    fontWeight = FontWeight.Bold
)

private val DashboardLevelStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 20.sp,
    lineHeight = 24.sp,
    fontWeight = FontWeight.ExtraBold
)

private val DashboardStatValueStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 15.sp,
    lineHeight = 19.sp,
    fontWeight = FontWeight.ExtraBold
)

private val DashboardMicroBoldStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 9.sp,
    lineHeight = 12.sp,
    fontWeight = FontWeight.Bold
)

private val DashboardActionStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 15.sp,
    lineHeight = 19.sp,
    fontWeight = FontWeight.Bold
)

private val DashboardSectionTitleStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 16.sp,
    lineHeight = 21.sp,
    fontWeight = FontWeight.ExtraBold
)

private val DashboardKnowledgeTitleStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 14.sp,
    lineHeight = 17.sp,
    fontWeight = FontWeight.ExtraBold
)

private val DashboardPillStyle = TextStyle(
    fontFamily = InterFontFamily,
    fontSize = 9.sp,
    lineHeight = 11.sp,
    fontWeight = FontWeight.Bold
)
