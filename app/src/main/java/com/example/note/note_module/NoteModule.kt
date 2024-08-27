package com.example.note.note_module

import com.example.note.NoteDatabase
import com.example.note.note_module.data.repository.NoteRepositoryImpl
import com.example.note.note_module.domain.repository.NoteRepository
import com.example.note.note_module.domain.usecase.NoteUseCase
import com.example.note.note_module.presentation.note.NoteViewModel
import org.koin.dsl.module

val noteModule
    get() = module {
        includes(
            noteRepositoryModule,
            useCaseModule,
            noteViewModelModule
        )
    }

val noteRepositoryModule
    get() = module {
        single { get<NoteDatabase>().noteDao() }
        single<NoteRepository> { NoteRepositoryImpl(get()) }
    }

val useCaseModule
    get() = module {
        single { NoteUseCase(get()) }
    }

val noteViewModelModule
    get() = module {
        single { NoteViewModel(get()) }
    }