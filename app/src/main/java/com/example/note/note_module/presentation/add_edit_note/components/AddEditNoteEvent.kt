package com.example.note.note_module.presentation.add_edit_note.components

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class EnterTitle(
        val value: String,
    ) : AddEditNoteEvent()

    data class EnterContent(
        val value: String,
    ) : AddEditNoteEvent()

    data class ChangeColor(
        val value: Int,
    ) : AddEditNoteEvent()

    data class ChangeTitleFocus(
        val focusState: FocusState,
    ) : AddEditNoteEvent()

    data class ChangeContentFocus(
        val focusState: FocusState,
    ) : AddEditNoteEvent()

    object SaveNote : AddEditNoteEvent()
}
