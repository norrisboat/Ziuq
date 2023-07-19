package com.norrisboat.ziuq.android.di

import com.norrisboat.ziuq.presentation.login.LoginViewModel
import com.norrisboat.ziuq.presentation.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
}