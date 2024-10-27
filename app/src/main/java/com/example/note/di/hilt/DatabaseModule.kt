package com.example.note.di.hilt

import android.content.Context
import androidx.room.Room
import com.example.note.core.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(
        @ApplicationContext
        context: Context,
    ): NoteDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "note_database",
            ).build()

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: NoteDatabase) = noteDatabase.noteDao()

    @Provides
    @Singleton
    fun provideAccountDao(noteDatabase: NoteDatabase) = noteDatabase.accountDao()
}
