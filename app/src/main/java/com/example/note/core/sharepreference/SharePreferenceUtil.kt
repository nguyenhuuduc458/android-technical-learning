package com.example.note.core.sharepreference

import android.content.SharedPreferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object SharePreferenceUtil : KoinComponent {
    private val sharedPreferences: SharedPreferences by inject()
    var currentLoginAccountId: Int by sharedPreferences.delegates.int("currentAccountId")
}
