package com.example.note.account_module.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.note.account_module.domain.model.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun register(account: Account)

    @Query("SELECT * FROM tbl_account WHERE username = :username AND password = :password")
    suspend fun login(username: String, password: String): Account?

    @Transaction
    suspend fun deleteAccountAndNote(account: Account) {
        deleteNotesForAccount(account.accountId)
        delete(account)
    }

    @Query("SELECT * FROM tbl_account WHERE account_id = :accountId")
    suspend fun findById(accountId: Int?): Account?

    @Query("SELECT * FROM tbl_account WHERE username = :username")
    suspend fun findByUsername(username: String): Account?

    @Delete
    suspend fun delete(account: Account)

    @Query("DELETE FROM tbl_account WHERE account_id = :accountId")
    suspend fun deleteNotesForAccount(accountId: Int)
}