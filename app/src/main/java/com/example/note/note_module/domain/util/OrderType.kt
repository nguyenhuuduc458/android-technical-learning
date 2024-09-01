package com.example.note.note_module.domain.util

sealed class OrderType {
    object Ascending : OrderType()

    object Descending : OrderType()
}
