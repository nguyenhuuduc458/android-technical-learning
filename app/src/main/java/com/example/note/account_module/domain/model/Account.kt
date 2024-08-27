package com.example.note.account_module.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "account_id")
    val accountId: Int = 0,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "password")
    val password: String
)