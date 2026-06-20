package com.example.mobile.ui.screens.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenButtonStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.viewmodel.NewFlashcardViewModel

@Composable
fun NewFlashcardScreen(
    noteId: Int? = null,
    cardId: Int? = null,
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    viewModel: NewFlashcardViewModel = viewModel()
) {
    val isEditing = cardId != null
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf("Easy") }

    LaunchedEffect(cardId) {
        cardId?.let { viewModel.loadCard(it) }
    }

    LaunchedEffect(viewModel.loadedCard) {
        viewModel.loadedCard?.let { card ->
            question = card.question
            answer = card.answer
            difficulty = card.difficulty
        }
    }

    LaunchedEffect(viewModel.saved) {
        if (viewModel.saved) onSaved()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            if (isEditing) "Edit Review Card" else "New Review Card",
            color = NSyncBlue,
            style = ScreenHeroStyle
        )
        Text(
            if (isEditing) "Update this question and answer." else "Create a question and answer for this knowledge item.",
            style = ScreenBodyStyle
        )

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Question") }
        )
        OutlinedTextField(
            value = answer,
            onValueChange = { answer = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            label = { Text("Answer") }
        )
        OutlinedTextField(
            value = difficulty,
            onValueChange = { difficulty = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Difficulty") }
        )

        viewModel.error?.let { message ->
            Text(message, color = Color(0xFFD21F2B), style = ScreenBodyStyle)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel", style = ScreenButtonStyle)
            }
            Button(
                onClick = {
                    if (isEditing) {
                        viewModel.loadedCard?.let { card ->
                            viewModel.updateFlashcard(card, question, answer, difficulty)
                        }
                    } else {
                        viewModel.saveFlashcard(noteId ?: return@Button, question, answer, difficulty)
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = NSyncBlue)
            ) {
                Text(
                    if (viewModel.isSaving) "Saving..." else if (isEditing) "Update Card" else "Save Card",
                    style = ScreenButtonStyle
                )
            }
        }
    }
}
