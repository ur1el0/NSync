package com.example.mobile.ui.screens.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.ui.components.CenteredCard
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.ScreenTitle
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.viewmodel.ReviewSessionViewModel

@Composable
fun ReviewSessionScreen(
    onCompleteClick: (score: Int, totalQuestions: Int, xpEarned: Int) -> Unit,
    noteId: Int? = null,
    cardId: Int? = null,
    viewModel: ReviewSessionViewModel = viewModel()
) {
    LaunchedEffect(noteId, cardId) { viewModel.loadSession(noteId, cardId) }
    LaunchedEffect(viewModel.completedResult) {
        viewModel.completedResult?.let { result ->
            viewModel.consumeCompletedResult()
            onCompleteClick(result.score, result.totalQuestions, result.xpEarned)
        }
    }
    var showAnswer by remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.currentIndex) {
        showAnswer = false
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(NSyncLightBackground)
            .padding(horizontal = 28.dp, vertical = 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Text("+${viewModel.cards.size * 25} XP", color = NSyncBlue, style = ScreenSectionStyle)
            Text(
                text = when {
                    viewModel.isLoading -> "Loading review cards"
                    viewModel.currentCard == null -> "No review cards available"
                    showAnswer -> "Review Answer"
                    else -> "Card ${viewModel.currentIndex + 1} of ${viewModel.cards.size}"
                },
                color = NSyncMutedText,
                style = ScreenBodyStyle
            )
        }

        viewModel.error?.let { message ->
            item {
                Text(message, color = androidx.compose.ui.graphics.Color(0xFFD21F2B), style = ScreenBodyStyle)
            }
        }

        viewModel.currentCard?.let { card ->
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
                        text = if (showAnswer) card.answer else card.question,
                        color = ScreenTitle,
                        style = ScreenHeroStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }
            }

            if (!showAnswer) {
                item {
                    PrimaryScreenButton(
                        text = "Show Answer",
                        onClick = { showAnswer = true }
                    )
                }
            } else {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { viewModel.recordAnswer(recalled = false) },
                            modifier = Modifier.weight(1f),
                            enabled = !viewModel.isCompleting
                        ) {
                            Text("Review again", style = ScreenBodyStyle)
                        }
                        Button(
                            onClick = { viewModel.recordAnswer(recalled = true) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(containerColor = NSyncBlue),
                            enabled = !viewModel.isCompleting
                        ) {
                            Text(
                                if (viewModel.isCompleting) "Saving..." else "Got it",
                                style = ScreenBodyStyle
                            )
                        }
                    }
                }
            }
        } ?: item {
            if (!viewModel.isLoading) {
                Text("Create a review card from a knowledge note to begin.", color = NSyncMutedText, style = ScreenBodyStyle)
            }
        }
    }
}
