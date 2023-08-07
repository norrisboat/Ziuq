package com.norrisboat.ziuq.presentation.difficulty

import com.norrisboat.ziuq.data.ui.QuizDifficulty

sealed interface DifficultyScreenState {

    object Loading : DifficultyScreenState

    object Idle : DifficultyScreenState

    data class Success(val difficulties: List<QuizDifficulty>) : DifficultyScreenState

    data class Error(val errorMessage: String) : DifficultyScreenState


}

