package com.norrisboat.ziuq.android.di

import com.norrisboat.ziuq.presentation.difficulty.DifficultyViewModel
import com.norrisboat.ziuq.presentation.home.HomeViewModel
import com.norrisboat.ziuq.presentation.login.LoginViewModel
import com.norrisboat.ziuq.presentation.quiz.CreateQuizViewModel
import com.norrisboat.ziuq.presentation.quiz.LiveQuizViewModel
import com.norrisboat.ziuq.presentation.quiz.QuizViewModel
import com.norrisboat.ziuq.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { HomeViewModel() }
    viewModel { DifficultyViewModel() }
    viewModel { CreateQuizViewModel() }
    viewModel { QuizViewModel() }
    viewModel { LiveQuizViewModel() }
}