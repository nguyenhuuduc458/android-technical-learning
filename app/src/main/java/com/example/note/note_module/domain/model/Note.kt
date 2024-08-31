package com.example.note.note_module.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.note.account_module.domain.model.Account
import com.example.note.ui.theme.BabyBlue
import com.example.note.ui.theme.LightGreen
import com.example.note.ui.theme.RedOrange
import com.example.note.ui.theme.RedPink
import com.example.note.ui.theme.Violet
import java.util.Date

@Entity(
    tableName = "tbl_note",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["account_id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["user_id"])]
)
data class Note(
    @ColumnInfo(name = "note_id")
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    @ColumnInfo(name = "user_id")
    val userId: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "color")
    val color: Int,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}
