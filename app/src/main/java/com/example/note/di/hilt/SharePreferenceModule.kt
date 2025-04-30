package com.example.note.di.hilt

import android.content.Context
import android.content.SharedPreferences
import com.example.note.core.sharepreference.AuthPreferences
import com.example.note.core.sharepreference.SecureSharedPreferenceUtil
import com.example.note.core.sharepreference.SharePreferenceUtil
import com.example.note.core.sharepreference.UserSettingsPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharePreferenceModule {
    @Provides
    @Singleton
    @AuthPreferences
    fun provideAuthPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @UserSettingsPreferences
    fun provideUserSettingsPreferences(
        @ApplicationContext context: Context,
    ): SharedPreferences = context.getSharedPreferences("userSettings", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharePreferenceUtil(
        @UserSettingsPreferences sharedPreferences: SharedPreferences,
    ): SharePreferenceUtil = SharePreferenceUtil(sharedPreferences)

    @Provides
    @Singleton
    fun provideSecureSharePreferenceUtil(
        @AuthPreferences secureSharePreferenceDelegates: SharedPreferences,
    ): SecureSharedPreferenceUtil = SecureSharedPreferenceUtil(secureSharePreferenceDelegates)
}
