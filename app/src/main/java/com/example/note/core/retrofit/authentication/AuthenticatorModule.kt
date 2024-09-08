package com.example.note.core.retrofit.authentication

import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val tokenServiceModule =
    module {
        single { get<Retrofit>(qualifier = named("retrofit_token_endpoint")).create(TokenApiService::class.java) }
    }
