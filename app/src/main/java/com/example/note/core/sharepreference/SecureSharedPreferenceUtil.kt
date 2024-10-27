package com.example.note.core.sharepreference

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SecureSharedPreferenceUtil
    @Inject
    constructor(
        @AuthPreferences private val secureSharePreferenceDelegates: SharedPreferences,
    ) {
        val accessToken by SecureSharePreferenceDelegates(
            secureSharePreferenceDelegates,
            "accessToken",
            "",
            "token",
        )
        val expiresIn by SecureSharePreferenceDelegates(
            secureSharePreferenceDelegates,
            "expiresIn",
            "",
            "expiresIn",
        )
    }
