package com.example.note.di

import android.app.Application
import androidx.room.Room
import com.example.note.core.database.NoteDatabase
import org.koin.dsl.module

val databaseModule get() =
    module {
        single {
            Room
                .databaseBuilder(
                    get<Application>(),
                    NoteDatabase::class.java,
                    "NoteDateBase",
                ).build()
        }
    }
