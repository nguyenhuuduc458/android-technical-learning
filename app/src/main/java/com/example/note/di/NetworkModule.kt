package com.example.note.di

import com.example.note.core.retrofit.NoteUri
import com.example.note.core.retrofit.OAuthAuthenticator
import com.example.note.core.retrofit.authentication.tokenServiceModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule
    get() =
        module {
            includes(
                httpClientModule,
                retrofitTokenModule,
                retrofitApiModule,
                tokenServiceModule,
            )
        }

val httpClientModule
    get() =
        module {
            // Set up a logging interceptor for debugging (optional)
            val loggingInterceptor =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }

            single {
                OkHttpClient
                    .Builder()
                    .addInterceptor(loggingInterceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .authenticator(OAuthAuthenticator())
                    .build()
            }
        }

val retrofitTokenModule
    get() =
        module {
            // retrofit to request token endpoint
            single(qualifier = named("retrofit_token_endpoint")) {
                Retrofit
                    .Builder()
                    .baseUrl(NoteUri.TOKEN_API_ENDPOINT) // Token base URL
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }

val retrofitApiModule
    get() =
        module {
            // retrofit to request api endpoint
            single(qualifier = named("retrofit_api_endpoint")) {
                Retrofit
                    .Builder()
                    .baseUrl(NoteUri.BASE_ENDPOINT_V1) // API base URL
                    .client(get())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }
