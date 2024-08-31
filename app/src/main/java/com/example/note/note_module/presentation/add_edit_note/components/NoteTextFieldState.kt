package com.example.note.note_module.presentation.add_edit_note.components

import androidx.compose.ui.graphics.toArgb
import com.example.note.note_module.domain.model.Note

data class NoteTextFieldState(
    val title: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = false,
    val content: String = "",
    val color: Int = Note.noteColors.random().toArgb(),
)
