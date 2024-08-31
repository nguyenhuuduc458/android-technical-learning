package com.example.note.account_module.domain.usecase.data

import com.example.note.account_module.domain.model.Account
import com.example.note.account_module.domain.repository.AccountRepository

class FakeAccountRepository : AccountRepository {
    val accounts = mutableListOf<Account>()

    override suspend fun register(
        username: String,
        password: String,
    ) {
        accounts.add(Account(username = username, password = password))
    }

    override suspend fun login(
        username: String,
        password: String,
    ): Account? =
        accounts.find {
            it.username == username && it.password == password
        }

    override suspend fun deleteAccountAndNote(account: Account) {
        accounts.remove(account)
    }

    override suspend fun isExistAccount(username: String): Boolean = accounts.any { it.username == username }
}
