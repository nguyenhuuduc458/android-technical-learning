package com.example.note.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedPreferences
    get() =
        module {
            // this preference is used to store small data
            single<SharedPreferences> {
                androidContext().getSharedPreferences(
                    "note_shared_preference",
                    Context.MODE_PRIVATE,
                )
            }

            // this preference is used to store secure data and small
            single<SharedPreferences>(qualifier = named("secure_prefs")) {
                androidContext().getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
            }
        }
