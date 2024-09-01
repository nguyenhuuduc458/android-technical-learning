package com.example.note.note_module.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.note.core.sharepreference.SharePreferenceUtil.currentLoginAccountId
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.usecase.NoteUseCase
import com.example.note.note_module.presentation.add_edit_note.components.AddEditNoteEvent
import com.example.note.note_module.presentation.add_edit_note.components.NoteTextFieldState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class AddEditNoteViewModel(
    private val noteUseCase: NoteUseCase,
) : ViewModel() {
    private val _noteTitle = MutableStateFlow(NoteTextFieldState(hint = "Enter note title"))
    val noteTitle: StateFlow<NoteTextFieldState> = _noteTitle.asStateFlow()

    private val _noteContent = MutableStateFlow(NoteTextFieldState(hint = "Enter content..."))
    val noteContent: StateFlow<NoteTextFieldState> = _noteContent.asStateFlow()

    private val _noteColor = MutableStateFlow(Note.noteColors.random().toArgb())
    val noteColor: StateFlow<Int> = _noteColor.asStateFlow()

    private val _eventFlow = MutableSharedFlow<NoteUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int = -1

    fun getNote(noteId: Int = -1) {
        if (noteId == -1) return
        viewModelScope.launch {
            noteUseCase.findNoteById(noteId)?.also { note ->
                currentNoteId = note.noteId
                _noteTitle.update {
                    it.copy(title = note.title, isHintVisible = false)
                }
                _noteContent.update {
                    it.copy(content = note.description, isHintVisible = false)
                }
                _noteColor.value = note.color
            }
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeColor -> {
                _noteColor.value = event.value
            }

            is AddEditNoteEvent.EnterContent -> {
                _noteContent.update {
                    it.copy(content = event.value, isHintVisible = false)
                }
            }

            is AddEditNoteEvent.EnterTitle -> {
                _noteTitle.update {
                    it.copy(title = event.value, isHintVisible = false)
                }
            }

            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.insertNote(
                            Note(
                                title = _noteTitle.value.title,
                                description = _noteContent.value.content,
                                color = _noteColor.value,
                                userId = currentLoginAccountId,
                                createdAt = Date(),
                                noteId = if (currentNoteId == -1) 0 else currentNoteId,
                            ),
                        )
                        _eventFlow.emit(NoteUiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(NoteUiEvent.ShowSnackbar("Couldn't save note with error ${e.message}"))
                    }
                }
            }

            is AddEditNoteEvent.ChangeContentFocus -> {
                _noteContent.update {
                    it.copy(isHintVisible = it.content.isBlank() && !event.focusState.isFocused)
                }
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.update {
                    it.copy(isHintVisible = it.title.isBlank() && !event.focusState.isFocused)
                }
            }
        }
    }
}

sealed class NoteUiEvent {
    data class ShowSnackbar(
        val message: String,
    ) : NoteUiEvent()

    object SaveNote : NoteUiEvent()
}
