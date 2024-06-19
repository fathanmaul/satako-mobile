package dev.capstone.satako_mobile.utils

import android.text.Editable
import android.text.TextWatcher

interface TextListener : TextWatcher {
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onTextListener(s)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(s: Editable?) {}

    fun onTextListener(s: CharSequence?)
}