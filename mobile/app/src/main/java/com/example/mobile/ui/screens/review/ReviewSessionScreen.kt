package com.example.mobile.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mobile.data.SampleData
import com.example.mobile.ui.components.CenteredCard
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText

@Composable
fun ReviewSessionScreen(
    onCompleteClick: () -> Unit
) {
    val card = SampleData.reviewSessionCard
    var showAnswer by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(NSyncLightBackground)
            .padding(horizontal = 28.dp, vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text("+25 XP", color = NSyncBlue, style = ScreenSectionStyle)
            Text(
                text = if (showAnswer) "Review Answer" else "Review Session",
                color = NSyncMutedText,
                style = ScreenBodyStyle
            )
        }

        item {
            CenteredCard(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .height(280.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (showAnswer) SampleData.draftReviewCardAnswer else card.question,
                        color = ScreenTitle,
                        style = ScreenHeroStyle,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (showAnswer) "Rate your recall" else "Tap to reveal the answer",
                        color = NSyncMutedText,
                        style = ScreenBodyStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        item {
            PrimaryScreenButton(
                text = if (showAnswer) "Hide Answer" else "Show Answer",
                onClick = {
                    if (showAnswer) {
                        showAnswer = false
                    } else {
                        showAnswer = true
                    }
                }
            )
        }

        if (!showAnswer) {
            item {
                Text(
                    text = "Skip for now",
                    color = NSyncBlue,
                    style = ScreenBodyStyle,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable(onClick = onCompleteClick)
                )
            }
        } else {
            item {
                Text(
                    text = "Finish review",
                    color = NSyncBlue,
                    style = ScreenBodyStyle,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .clickable(onClick = onCompleteClick)
                )
            }
        }
    }
}
