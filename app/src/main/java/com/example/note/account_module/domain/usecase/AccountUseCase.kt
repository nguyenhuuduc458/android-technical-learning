package com.example.note.account_module.domain.usecase

import com.example.note.account_module.domain.model.Account
import com.example.note.account_module.domain.repository.AccountRepository

class AccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun login(username: String, password: String): Account? {
        return accountRepository.login(username, password)
    }

    suspend fun register(username: String, password: String) {
        accountRepository.register(username, password)
    }

    suspend fun unregister(account: Account) {
        accountRepository.deleteAccountAndNote(account)
    }

    suspend fun isExistAccount(username: String): Boolean {
        return accountRepository.isExistAccount(username)
    }
}