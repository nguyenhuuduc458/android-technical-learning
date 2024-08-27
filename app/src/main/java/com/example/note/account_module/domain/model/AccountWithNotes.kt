package com.example.note.account_module.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.note.note_module.domain.model.Note

data class AccountWithNotes(
    @Embedded val account: Account,
    @Relation(
        parentColumn = "accountId",
        entityColumn = "accountCreatorId"
    )
    val notes: List<Note>
)