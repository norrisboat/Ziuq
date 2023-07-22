package com.norrisboat.ziuq.presentation.home

import com.norrisboat.ziuq.data.ui.QuizCategory

sealed interface HomeScreenState {

    object Loading : HomeScreenState

    object Idle : HomeScreenState

    data class Success(val categories: List<QuizCategory>) : HomeScreenState

    data class Error(val errorMessage: String) : HomeScreenState


}

