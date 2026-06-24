package com.example.mobile.ui.screens.knowledge

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.data.KnowledgeItem
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.KnowledgeListCard
import com.example.mobile.ui.viewmodel.KnowledgeBaseViewModel

@Composable
fun KnowledgeBaseScreen(
    onKnowledgeClick: (KnowledgeItem) -> Unit,
    onNewNoteClick: () -> Unit,
    onRouteClick: (String) -> Unit,
    viewModel: KnowledgeBaseViewModel = viewModel()
) {
    val uiState = viewModel.uiState

    MainScreenScaffold(
        currentRoute = Routes.KNOWLEDGE_BASE,
        title = "Knowledge Base",
        subtitle = "Capture, search, and organize important ideas.",
        onRouteClick = onRouteClick
    ) {
        if (uiState.isLoading) {
            item {
                Text("Loading knowledge...")
            }
        }

        uiState.error?.let { message ->
            item {
                Text("Unable to load knowledge: $message")
            }
        }

        if (!uiState.isLoading && uiState.error == null && uiState.items.isEmpty()) {
            item {
                Text("No knowledge saved yet.")
            }
        }

        items(uiState.items) { item ->
            KnowledgeListCard(
                item = item,
                onClick = { onKnowledgeClick(item) }
            )
        }

        item {
            PrimaryScreenButton(
                text = "+ New Note",
                onClick = onNewNoteClick
            )
        }
    }
}
