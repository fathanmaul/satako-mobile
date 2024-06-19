package dev.capstone.satako_mobile.utils

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import dev.capstone.satako_mobile.R

object ValidationAuth {

    fun isFieldEmpty(field: String, errorView: TextView, errorMessage: String): Boolean {
        return if (field.isEmpty()) {
            showText(errorView, true, errorMessage)
            true
        } else {
            showText(errorView, false)
            false
        }
    }

    fun validateEmail(context: Context, email: String, errorView: TextView): Boolean {
        return when {
            email.isEmpty() -> {
                showText(errorView, true, context.getString(R.string.email_empty))
                false
            }

            !email.isEmailValid() -> {
                showText(errorView, true, context.getString(R.string.invalid_email))
                false
            }

            else -> {
                showText(errorView, false)
                true
            }
        }
    }

    fun validatePassword(context: Context, password: String, errorView: TextView): Boolean {
        return when {
            password.isEmpty() -> {
                showText(errorView, true, context.getString(R.string.password_empty))
                false
            }

            !password.isPasswordValid() -> {
                showText(errorView, true, context.getString(R.string.password_invalid))
                false
            }

            else -> {
                showText(errorView, false)
                true
            }
        }
    }

    fun validatePasswordMatch(
        context: Context,
        password: String,
        confirmPassword: String,
        errorView: TextView
    ): Boolean {
        return when {
            confirmPassword.isEmpty() -> {
                showText(errorView, true, context.getString(R.string.confirm_password_empty))
                false
            }

            password != confirmPassword -> {
                showText(errorView, true, context.getString(R.string.password_not_match))
                false
            }

            else -> {
                showText(errorView, false)
                true
            }
        }
    }

    fun validateName(context: Context, name: String, errorView: TextView): Boolean {
        return when {
            name.isEmpty() -> {
                showText(errorView, true, context.getString(R.string.name_empty))
                false
            }

            !name.isNameValid() -> {
                showText(errorView, true, context.getString(R.string.name_invalid))
                false
            }

            else -> {
                showText(errorView, false)
                true
            }
        }
    }

    private fun showText(binding: TextView?, state: Boolean, msg: String? = null) {
        if (state) {
            if (msg != null) binding?.text = msg
            binding?.show()
        } else {
            binding?.gone()

        }
    }
}