package com.example.note.core.sharepreference

import android.content.SharedPreferences
import javax.inject.Inject

class SharePreferenceUtil
    @Inject
    constructor(
        @UserSettingsPreferences private val sharedPreferences: SharedPreferences,
    ) {
        var currentLoginAccountId: Int by sharedPreferences.delegates.int("currentAccountId")
    }
