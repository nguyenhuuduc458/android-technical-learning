package com.example.note.di.hilt

import com.example.note.core.retrofit.OAuthAuthenticator
import com.example.note.core.retrofit.authentication.TokenApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RetrofitModule {
    @Binds
    @Singleton
    fun provideAuthenticator(authenticator: OAuthAuthenticator): Authenticator

    companion object {
        @Provides
        @Singleton
        fun provideOkHttpClient(authenticator: OAuthAuthenticator): OkHttpClient {
            val logging =
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            return OkHttpClient
                .Builder()
                .addInterceptor(logging)
                .authenticator(authenticator) // Add the authenticator here
                .build()
        }

        @Provides
        @Singleton
        fun provideTokenApiService(retrofit: Retrofit): TokenApiService =
            retrofit.create(
                TokenApiService::class.java,
            )

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit
                .Builder()
                .baseUrl("https://accounts.spotify.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
