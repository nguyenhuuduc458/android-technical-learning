package com.example.note.note_module.presentation.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.core.sharepreference.SharePreferenceUtil
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.usecase.NoteUseCase
import com.example.note.note_module.domain.util.NoteEvent
import com.example.note.note_module.domain.util.NoteOrder
import com.example.note.note_module.domain.util.NoteState
import com.example.note.note_module.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel
    @Inject
    constructor(
        private val noteUseCase: NoteUseCase,
        private val sharePreferenceUtil: SharePreferenceUtil,
    ) : ViewModel() {
        private val _noteState = MutableStateFlow(NoteState())
        val noteState: StateFlow<NoteState> get() = _noteState.asStateFlow()

        private var getNoteJob: Job? = null
        private var recentDeleteNote: Note? = null

        init {
            getNote(NoteOrder.Date(OrderType.Descending))
        }

        fun onEvent(noteEvent: NoteEvent) {
            when (noteEvent) {
                is NoteEvent.DeleteNote -> {
                    viewModelScope.launch {
                        noteUseCase.deleteNote(noteEvent.note)
                        recentDeleteNote = noteEvent.note
                    }
                }

                is NoteEvent.InsertNote -> {
                }

                is NoteEvent.Order -> {
                    getNote(noteEvent.noteOrder)
                }

                NoteEvent.RestoreNote -> {
                    viewModelScope.launch {
                        recentDeleteNote?.let {
                            noteUseCase.insertNote(it)
                            recentDeleteNote = null
                        }
                    }
                }

                NoteEvent.ToggleOrderSection -> {
                    viewModelScope.launch {
                        _noteState.update {
                            it.copy(
                                isOrderSectionVisible = !it.isOrderSectionVisible,
                            )
                        }
                    }
                }
            }
        }

        private fun getNote(noteOrder: NoteOrder) {
            check(sharePreferenceUtil.currentLoginAccountId > 0) { "Account id must be greater than 0" }
            getNoteJob?.cancel()
            getNoteJob =
                noteUseCase
                    .getNotesWithOrder(sharePreferenceUtil.currentLoginAccountId, noteOrder)
                    .onStart {
                        println("start get note")
                    }.onEach { notes ->
                        _noteState.update {
                            it.copy(notes = notes, noteOrder = noteOrder)
                        }
                    }.catch {
                    }.launchIn(viewModelScope)
        }
    }
