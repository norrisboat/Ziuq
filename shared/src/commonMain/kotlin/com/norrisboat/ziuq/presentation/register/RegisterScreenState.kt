package com.norrisboat.ziuq.presentation.register

sealed interface RegisterScreenState {

    object Loading : RegisterScreenState

    object Idle : RegisterScreenState

    object Success : RegisterScreenState

    data class Error(val errorMessage: String) : RegisterScreenState


}

