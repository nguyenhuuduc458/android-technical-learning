package com.example.note.note_module.domain.repository

import com.example.note.note_module.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNoteByAccountId(accountId: Int): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun findById(noteId: Int): Note?
}
