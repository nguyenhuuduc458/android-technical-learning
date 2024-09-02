package com.example.note.core.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val okHttpClient =
    OkHttpClient
        .Builder()
        .authenticator(OAuthAuthenticator())
        .build()

// Retrofit instance for the token endpoint
val tokenRetrofit: Retrofit =
    Retrofit
        .Builder()
        .baseUrl(NoteUri.TOKEN_API_ENDPOINT) // Token base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

// Retrofit instance for the API endpoint
val apiRetrofit: Retrofit =
    Retrofit
        .Builder()
        .baseUrl(NoteUri.BASE_ENDPOINT_V1) // API base URL
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
