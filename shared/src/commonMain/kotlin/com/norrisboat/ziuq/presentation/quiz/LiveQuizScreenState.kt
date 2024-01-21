package com.norrisboat.ziuq.presentation.quiz

import com.norrisboat.ziuq.data.ui.QuizUI

sealed interface LiveQuizScreenState {
    object Idle : LiveQuizScreenState
    object Loading : LiveQuizScreenState
    object CreatingQuiz : LiveQuizScreenState
    object QuizCreated : LiveQuizScreenState
    object JoiningQuiz : LiveQuizScreenState
    object LoadingQuestions : LiveQuizScreenState
    object WaitingForOpponent : LiveQuizScreenState

    data class Error(val errorMessage: String) : LiveQuizScreenState

}

sealed interface LiveQuizScreenNavigation {
    data class QuizReady(val quizUI: QuizUI) : LiveQuizScreenNavigation
}

