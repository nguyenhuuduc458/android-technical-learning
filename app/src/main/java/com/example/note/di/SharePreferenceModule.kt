package com.example.note.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferences
    get() = module {
        single<SharedPreferences> {
            androidContext().getSharedPreferences("note_shared_preference", Context.MODE_PRIVATE)
        }
    }