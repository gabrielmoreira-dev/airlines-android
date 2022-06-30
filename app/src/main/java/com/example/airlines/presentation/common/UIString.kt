package com.example.airlines.presentation.common

import android.content.Context
import androidx.annotation.StringRes

sealed class UIString {
    data class DynamicString(val value: String) : UIString()
    class StringResource(@StringRes val resId: Int, vararg val args: Any): UIString()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId, *args)
        }
    }
}