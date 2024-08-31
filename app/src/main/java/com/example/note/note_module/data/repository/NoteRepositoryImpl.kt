package com.example.note.note_module.data.repository

import com.example.note.note_module.data.data_source.NoteDao
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepositoryImpl
    @OptIn(ExperimentalCoroutinesApi::class)
    constructor(
        private val noteDao: NoteDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1),
    ) : NoteRepository {
        override fun getNoteByAccountId(accountId: Int): Flow<List<Note>> = noteDao.getNoteByAccountId(accountId)

        override suspend fun insertNote(note: Note) {
            check(note.title.isBlank()) { "Title is not null or empty" }
            check(note.title.isBlank()) { "Description is not null or empty" }
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
