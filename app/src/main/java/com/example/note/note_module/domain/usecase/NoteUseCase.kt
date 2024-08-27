package com.example.note.note_module.domain.usecase

import android.adservices.adid.AdId
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.repository.NoteRepository
import com.example.note.note_module.domain.util.NoteOrder
import com.example.note.note_module.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale

class NoteUseCase(
    private val noteRepository: NoteRepository,
) {
    fun getNotesWithOrder(
        accountId: Int,
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return noteRepository.getNoteByAccountId(accountId).map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.title.lowercase(Locale.getDefault()) }
                        is NoteOrder.Date -> notes.sortedBy { it.createdAt }
                        is NoteOrder.Color -> notes.sortedBy { it.color }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase(Locale.getDefault()) }
                        is NoteOrder.Date -> notes.sortedByDescending { it.createdAt }
                        is NoteOrder.Color -> notes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }

    suspend fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
    }

    suspend fun insertNote(note: Note) {
        noteRepository.insertNote(note)
    }

    suspend fun findNoteById(noteId: Int): Note? {
        return noteRepository.findById(noteId)
    }
}