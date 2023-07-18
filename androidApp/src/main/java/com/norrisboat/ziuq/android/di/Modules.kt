package com.norrisboat.ziuq.android.di

import com.norrisboat.ziuq.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { LoginViewModel() }
}