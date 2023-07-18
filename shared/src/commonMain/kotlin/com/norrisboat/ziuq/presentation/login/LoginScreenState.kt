package com.norrisboat.ziuq.presentation.login

sealed interface LoginScreenState {

    object Loading : LoginScreenState

    object Idle : LoginScreenState

    object Register : LoginScreenState

    object Success : LoginScreenState

    data class Error(val errorMessage: String) : LoginScreenState


}

