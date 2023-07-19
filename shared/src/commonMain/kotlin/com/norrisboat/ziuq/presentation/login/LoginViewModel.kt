package com.norrisboat.ziuq.presentation.login

import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.domain.usecase.LoginUseCase
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class LoginViewModel : KoinComponent, KMMViewModel() {

    private val loginUseCase: LoginUseCase by inject()
    private val settingsRepository: SettingsRepository by inject()

    private val _state = MutableStateFlow<LoginScreenState>(LoginScreenState.Idle)
    var state = _state.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.coroutineScope.launch {
            loginUseCase.run(params = LoginRequest(username, password)).map {
                when(it) {
                    is FlowResult.Success -> {
                        settingsRepository.setIsLoggedIn(true)
                        it.data.id?.let { userId -> settingsRepository.setUserId(userId)}

                        _state.value = LoginScreenState.Success
                    }

                    is FlowResult.Error -> {
                        _state.value = LoginScreenState.Error(it.exception)
                    }
                    FlowResult.Loading -> {
                        _state.value = LoginScreenState.Loading
                    }
                }
            }.collectLatest {  }
        }
    }

    fun openRegister() {
        _state.value = LoginScreenState.Register
    }

}