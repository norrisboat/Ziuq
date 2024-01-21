package com.norrisboat.ziuq.presentation.register

import com.norrisboat.ziuq.data.remote.request.RegisterRequest
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.domain.usecase.QuizSetupUseCase
import com.norrisboat.ziuq.domain.usecase.RegisterUseCase
import com.norrisboat.ziuq.domain.usecase.UseCase
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class RegisterViewModel : KoinComponent, KMMViewModel() {

    private val registerUseCase: RegisterUseCase by inject()
    private val quizSetupUseCase: QuizSetupUseCase by inject()
    private val settingsRepository: SettingsRepository by inject()

    private val _state = MutableStateFlow<RegisterScreenState>(RegisterScreenState.Idle)

    @NativeCoroutinesState
    var state = _state.stateIn(viewModelScope, WhileViewSubscribed, RegisterScreenState.Idle)

    fun register(username: String, password: String) {
        _state.value = RegisterScreenState.Loading
        viewModelScope.coroutineScope.launch {
            registerUseCase.run(params = RegisterRequest(username, password)).map {
                when (it) {
                    is FlowResult.Success -> {
                        settingsRepository.setIsLoggedIn(true)
                        it.data.id?.let { userId -> settingsRepository.setUserId(userId) }
                        it.data.username?.let { username -> settingsRepository.setUsername(username) }
                        setup()
                    }

                    is FlowResult.Error -> {
                        _state.value = RegisterScreenState.Error(it.exception)
                    }

                    FlowResult.Loading -> {
                        _state.value = RegisterScreenState.Loading
                    }
                }
            }.collectLatest { }
        }
    }

    private fun setup() {
        viewModelScope.coroutineScope.launch {
            quizSetupUseCase.run(UseCase.Nothing).map {
                when (it) {
                    is FlowResult.Success -> {
                        settingsRepository.setIsLoggedIn(true)
                        _state.value = RegisterScreenState.Success
                    }

                    is FlowResult.Error -> {
                        _state.value = RegisterScreenState.Error(it.exception)
                    }

                    FlowResult.Loading -> {
                        _state.value = RegisterScreenState.Loading
                    }
                }
            }.collectLatest { }
        }
    }

}