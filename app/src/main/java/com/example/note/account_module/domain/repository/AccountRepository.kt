package com.example.note.account_module.domain.repository

import com.example.note.account_module.domain.model.Account

interface AccountRepository {
    suspend fun register(
        username: String,
        password: String,
    )

    suspend fun login(
        username: String,
        password: String,
    ): Account?

    suspend fun deleteAccountAndNote(account: Account)

    suspend fun isExistAccount(username: String): Boolean
}
