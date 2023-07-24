package com.norrisboat.ziuq.presentation.quiz

import com.norrisboat.ziuq.data.remote.request.CreateQuizRequest
import com.norrisboat.ziuq.domain.usecase.CreateQuizUseCase
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class CreateQuizViewModel : KoinComponent, KMMViewModel() {

    private var creatingQuizJob: Job? = null
    private val createQuizUseCase: CreateQuizUseCase by inject()

    private val _state = MutableStateFlow<CreateQuizScreenState>(CreateQuizScreenState.Loading)
    var state =
        _state.stateIn(
            viewModelScope.coroutineScope,
            WhileViewSubscribed,
            CreateQuizScreenState.Loading
        )

    fun createQuiz(createQuizRequest: CreateQuizRequest) {
        if (creatingQuizJob == null) {
            creatingQuizJob = viewModelScope.coroutineScope.launch {
                createQuizUseCase.run(createQuizRequest).map {
                    when (it) {
                        is FlowResult.Success -> {
                            _state.value = CreateQuizScreenState.Success(it.data)
                        }

                        is FlowResult.Error -> {
                            _state.value = CreateQuizScreenState.Error(it.exception)
                        }

                        FlowResult.Loading -> {
                            _state.value = CreateQuizScreenState.Loading
                        }
                    }
                }.collectLatest { }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        creatingQuizJob?.cancel()
    }

}