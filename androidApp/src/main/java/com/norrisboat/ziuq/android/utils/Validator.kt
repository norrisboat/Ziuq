package com.norrisboat.ziuq.android.utils

interface Validator {
    fun validate(text: String): Boolean
    fun setError(message: String)
    fun getError(): String
}

class NonEmptyValidator(private var errorMessage: String = "Text cannot be empty") : Validator {
    override fun validate(text: String): Boolean {
        return text.isNotBlank()
    }

    override fun setError(message: String) {
        errorMessage = message
    }

    override fun getError(): String {
        return errorMessage
    }

}