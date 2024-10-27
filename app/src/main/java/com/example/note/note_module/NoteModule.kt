package com.example.note.note_module

import com.example.note.note_module.data.repository.NoteRepositoryImpl
import com.example.note.note_module.domain.repository.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NoteModule {
    @Binds
    @Singleton
    fun bindNoteRepository(noteRepositoryImpl: NoteRepositoryImpl): NoteRepository
}
