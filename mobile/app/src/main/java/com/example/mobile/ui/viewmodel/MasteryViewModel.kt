package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.CollectionMastery
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.MasteryUiState
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MasteryViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(MasteryUiState())
        private set

    init {
        loadMastery()
    }

    fun loadMastery() {
        viewModelScope.launch {
            uiState = MasteryUiState(isLoading = true)
            try {
                val progress = repository.getProgress()
                val reviewCards = repository.getReviewCards()
                val masteryGroups = reviewCards
                    .groupBy { it.collection }
                    .map { (collection, cards) ->
                        val noteCount = cards
                            .map { it.sourceNoteTitle }
                            .distinct()
                            .size

                        CollectionMastery(
                            collection = collection,
                            context = "${cards.size} cards across $noteCount notes",
                            masteryPercent = cards
                                .map { it.masteryPercent }
                                .average()
                                .roundToInt()
                        )
                    }
                uiState = MasteryUiState(
                    progress = progress,
                    masteryGroups = masteryGroups
                )
            } catch (e: Exception) {
                uiState = MasteryUiState(isLoading = false, error = e.message)
            }
        }
    }
}
