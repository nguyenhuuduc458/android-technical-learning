package com.example.note.note_module.data.repository

import com.example.note.di.hilt.IoDispatcher
import com.example.note.note_module.data.data_source.NoteDao
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NoteRepositoryImpl
    @Inject
    constructor(
        private val noteDao: NoteDao,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : NoteRepository {
        override fun getNoteByAccountId(accountId: Int): Flow<List<Note>> = noteDao.getNoteByAccountId(accountId)

        override suspend fun insertNote(note: Note) {
            check(note.title.isNotBlank()) { "Title is not null or empty" }
            check(note.description.isNotBlank()) { "Description is not null or empty" }
            withContext(defaultDispatcher) {
                noteDao.insertNote(note)
            }
        }

        override suspend fun deleteNote(note: Note) {
            withContext(defaultDispatcher) { noteDao.deleteNote(note) }
        }

        override suspend fun findById(noteId: Int): Note? {
            require(noteId > 0) { "Note with id $noteId must be greater than 0" }
            return withContext(defaultDispatcher) { noteDao.findById(noteId) }
        }
    }
