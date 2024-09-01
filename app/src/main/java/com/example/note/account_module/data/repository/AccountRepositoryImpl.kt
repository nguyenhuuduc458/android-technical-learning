package com.example.note.account_module.data.repository

import com.example.note.account_module.data.data_source.AccountDao
import com.example.note.account_module.domain.model.Account
import com.example.note.account_module.domain.repository.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.withContext

class AccountRepositoryImpl
    @OptIn(ExperimentalCoroutinesApi::class)
    constructor(
        private val accountDao: AccountDao,
        private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO.limitedParallelism(1),
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
