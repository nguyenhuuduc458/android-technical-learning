package com.example.note.account_module

import com.example.note.NoteDatabase
import com.example.note.account_module.data.repository.AccountRepositoryImpl
import com.example.note.account_module.domain.repository.AccountRepository
import com.example.note.account_module.domain.usecase.AccountUseCase
import com.example.note.account_module.presentation.login.LoginViewModel
import com.example.note.account_module.presentation.register.RegisterViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val accountModule
    get() = module {
        includes(
            repositoryModule,
            useCaseModule,
            viewModelModule
        )
    }

val repositoryModule
    get() = module {
        single { Dispatchers.IO }
        single { get<NoteDatabase>().accountDao() }
        single<AccountRepository> { AccountRepositoryImpl(get()) }
    }

val useCaseModule
    get() = module {
        single { AccountUseCase(get()) }
    }

val viewModelModule
    get() = module {
        viewModel { LoginViewModel(get()) }
        viewModel { RegisterViewModel(get()) }
    }