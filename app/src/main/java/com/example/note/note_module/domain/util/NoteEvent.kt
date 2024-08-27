package com.example.note.note_module.domain.util

import com.example.note.note_module.domain.model.Note

sealed class NoteEvent {
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    data class InsertNote(val note: Note): NoteEvent()
    object RestoreNote: NoteEvent()
    object ToggleOrderSection: NoteEvent()
}