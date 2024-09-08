package com.example.note.di

import com.example.note.account_module.accountModule
import com.example.note.note_module.noteModule
import org.koin.dsl.module

val appModule
    get() =
        module {
            includes(
                sharedPreferences,
                databaseModule,
                networkModule,
                accountModule,
                noteModule,
            )
        }
