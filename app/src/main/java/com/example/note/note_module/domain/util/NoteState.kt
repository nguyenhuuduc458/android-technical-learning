package com.example.note.note_module.domain.util

import com.example.note.note_module.domain.model.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
)
