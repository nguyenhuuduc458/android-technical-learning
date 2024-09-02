package com.example.note.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.note.account_module.data.data_source.AccountDao
import com.example.note.account_module.domain.model.Account
import com.example.note.note_module.data.data_source.NoteDao
import com.example.note.note_module.domain.model.Note
import com.example.note.note_module.domain.util.DateTimeConverter

@Database(
    entities = [Account::class, Note::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(DateTimeConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao

    abstract fun noteDao(): NoteDao
}
