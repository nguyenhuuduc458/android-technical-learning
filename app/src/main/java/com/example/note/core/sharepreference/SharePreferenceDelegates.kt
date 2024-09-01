package com.example.note.core.sharepreference

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

val SharedPreferences.delegates
    get() = SharedPreferenceDelegates(this)

class SharedPreferenceDelegates(
    private val prefs: SharedPreferences,
) {
    fun string(
        key: String? = null,
        default: String = "",
    ) = create(default, key, { k, d -> prefs.getString(k, d) as String }, prefs.edit()::putString)

    fun int(
        key: String? = null,
        default: Int = -1,
    ) = create(default, key, prefs::getInt, prefs.edit()::putInt)

    private fun <T : Any> create(
        default: T,
        key: String? = null,
        getter: (key: String, default: T) -> T,
        setter: (key: String, value: T) -> SharedPreferences.Editor,
    ) = object : ReadWriteProperty<Any, T> {
        private fun key(property: KProperty<*>) = key ?: property.name

        override fun getValue(
            thisRef: Any,
            property: KProperty<*>,
        ): T = getter(key(property), default)

        override fun setValue(
            thisRef: Any,
            property: KProperty<*>,
            value: T,
        ) {
            setter(key(property), value).apply()
        }
    }
}
