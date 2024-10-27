package com.example.note.account_module.data.repository

import com.example.note.account_module.data.data_source.AccountDao
import com.example.note.account_module.domain.model.Account
import com.example.note.account_module.domain.repository.AccountRepository
import com.example.note.di.hilt.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl
    @Inject
    constructor(
        private val accountDao: AccountDao,
        @IoDispatcher private val defaultDispatcher: CoroutineDispatcher,
    ) : AccountRepository {
        override suspend fun register(
            username: String,
            password: String,
        ) {
            withContext(defaultDispatcher) {
                val account = Account(username = username, password = password)
                accountDao.register(account)
            }
        }

        override suspend fun login(
            username: String,
            password: String,
        ): Account? =
            withContext(defaultDispatcher) {
                accountDao.login(username, password)
            }

        override suspend fun deleteAccountAndNote(account: Account) {
            withContext(defaultDispatcher) {
                deleteAccountAndNote(account)
            }
        }

        override suspend fun isExistAccount(username: String): Boolean =
            withContext(defaultDispatcher) {
                accountDao.findByUsername(username) != null
            }
    }
