package com.example.mobile.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobile.data.repository.NSyncRepository
import kotlinx.coroutines.launch


class NewNoteViewModel : ViewModel() {
    private val repository = NSyncRepository()

    var isSaving by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    var saved by mutableStateOf(false)
        private set

    var title by mutableStateOf("")
        private set

    var tag by mutableStateOf("")
        private set

    var content by mutableStateOf("")
        private set

    var loaded by mutableStateOf(false)
        private set

    fun createNote(title: String, content: String, tag: String) {
        viewModelScope.launch {
            isSaving = true
            error = null
            saved = false

            val createdNote = repository.createNote(title, content, tag)
            if (createdNote != null) {
                saved = true
            } else {
                error = "Unable to save note."
            }

            isSaving = false
        }
    }

    fun loadNote(id: Int) {
        if (loaded) return

        viewModelScope.launch {
            error = null
            val item = repository.getKnowledgeItemById(id)
            if (item != null) {
                title = item.title
                tag = item.collection
                content = item.fullNote
                loaded = true
            } else {
                error = "Unable to load note."
            }
        }
    }

    fun updateNote(id: Int, title: String, content: String, tag: String) {
        viewModelScope.launch {
            isSaving = true
            error = null
            saved = false

            val updatedNote = repository.updateNote(id, title, content, tag)
            if (updatedNote != null) {
                saved = true
            } else {
                error = "Unable to update note."
            }

            isSaving = false
        }
    }
}
