package com.example.mobile.ui.screens.knowledge

import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.mobile.data.KnowledgeItem
import com.example.mobile.data.SampleData
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.components.PrimaryScreenButton
import com.example.mobile.ui.components.KnowledgeListCard

@Composable
fun KnowledgeBaseScreen(
    onKnowledgeClick: (KnowledgeItem) -> Unit,
    onNewNoteClick: () -> Unit,
    onRouteClick: (String) -> Unit
) {
    MainScreenScaffold(
        currentRoute = Routes.KNOWLEDGE_BASE,
        title = "Knowledge Base",
        subtitle = "Capture, search, and organize important ideas.",
        onRouteClick = onRouteClick
    ) {
        items(SampleData.knowledgeItems) { item ->
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
