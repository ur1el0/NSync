package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.local.AuthSession
import com.example.mobile.data.remote.RetrofitClient
import com.example.mobile.data.repository.NSyncRepository
import com.example.mobile.ui.state.DashboardUiState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class DashboardViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var uiState by mutableStateOf(DashboardUiState())
        private set

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            val session = RetrofitClient.sessionStore.sessionFlow.first()
            uiState = DashboardUiState(
                isLoading = true,
                displayName = session.dashboardName(),
                email = session?.email.orEmpty()
            )
            try {
                val progress = repository.getProgress()
                val items = repository.getKnowledgeItems()
                uiState = DashboardUiState(
                    displayName = session.dashboardName(),
                    email = session?.email.orEmpty(),
                    progress = progress,
                    recentKnowledge = items
                )
            } catch (e: Exception) {
                uiState = DashboardUiState(
                    displayName = session.dashboardName(),
                    email = session?.email.orEmpty(),
                    error = e.message ?: "Unable to load dashboard."
                )
            }
        }
    }

    private fun AuthSession?.dashboardName(): String {
        return this?.displayName
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: this?.email
                ?.substringBefore("@")
                ?.takeIf { it.isNotEmpty() }
            ?: "there"
    }
}
