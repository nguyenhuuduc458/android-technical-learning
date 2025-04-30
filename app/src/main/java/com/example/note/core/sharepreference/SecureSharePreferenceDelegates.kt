package com.example.note.core.sharepreference

import android.content.SharedPreferences
import com.example.note.core.cryptographic.KeyStoreUtil.decryptData
import com.example.note.core.cryptographic.KeyStoreUtil.encryptData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
class SecureSharePreferenceDelegates<T>(
    private val preferences: SharedPreferences,
    private val key: String,
    private val defaultValue: T,
    private val alias: String,
) : ReadWriteProperty<Any, T> {
    override fun getValue(
        thisRef: Any,
        property: KProperty<*>,
    ): T {
        val encryptedData = preferences.getString(key, null)
        return if (encryptedData != null) {
            val decryptedData = decryptData(encryptedData, alias)
            when (defaultValue) {
                is String -> decryptedData as T
                is Int -> decryptedData.toInt() as T
                else -> defaultValue
            }
        } else {
            defaultValue
        }
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: T,
    ) {
        preferences
            .edit()
            .putString(key, encryptData(value.toString(), alias))
            .apply()
    }
}
