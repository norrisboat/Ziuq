package com.norrisboat.ziuq.presentation.register

import com.norrisboat.ziuq.data.remote.request.RegisterRequest
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.domain.usecase.RegisterUseCase
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class RegisterViewModel : KoinComponent, KMMViewModel() {

    private val registerUseCase: RegisterUseCase by inject()
    private val settingsRepository: SettingsRepository by inject()

    private val _state = MutableStateFlow<RegisterScreenState>(RegisterScreenState.Idle)
    var state = _state.asStateFlow()

    fun register(username: String, password: String) {
        viewModelScope.coroutineScope.launch {
            registerUseCase.run(params = RegisterRequest(username, password)).map {
                when (it) {
                    is FlowResult.Success -> {
                        settingsRepository.setIsLoggedIn(true)
                        it.data.id?.let { userId -> settingsRepository.setUserId(userId) }

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