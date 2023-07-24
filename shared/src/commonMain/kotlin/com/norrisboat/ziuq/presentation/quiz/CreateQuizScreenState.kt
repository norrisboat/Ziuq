package com.norrisboat.ziuq.presentation.quiz

import com.norrisboat.ziuq.data.ui.QuizUI

sealed interface CreateQuizScreenState {

    object Loading : CreateQuizScreenState

    data class Success(val quizUI: QuizUI) : CreateQuizScreenState

    data class Error(val errorMessage: String) : CreateQuizScreenState


}

