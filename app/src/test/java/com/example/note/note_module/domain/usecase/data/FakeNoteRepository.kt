package com.example.note.note_module.domain.usecase.data

import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeNoteRepository : NoteRepository {
    val notes = mutableListOf<Note>()

    override fun getNoteByAccountId(accountId: Int): Flow<List<Note>> = flow { emit(notes.filter { it.userId == accountId }) }

    override suspend fun insertNote(note: Note) {
        check(note.title.isNotBlank()) { "Title is not null or empty" }
        check(note.description.isNotBlank()) { "Description is not null or empty" }
        val oldNote = notes.find { it.noteId == note.noteId }
        if (oldNote != null) {
            notes.remove(oldNote)
        }
        notes.add(note)
    }

    override suspend fun deleteNote(note: Note) {
        notes.remove(note)
    }

    override suspend fun findById(noteId: Int): Note? {
        check(noteId > 0) { "Note with id $noteId must be greater than 0" }
        return notes.find { it.noteId == noteId }
    }
}
