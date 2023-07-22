package com.norrisboat.ziuq.presentation.difficulty

import com.norrisboat.ziuq.domain.usecase.GetDifficultyUseCase
import com.norrisboat.ziuq.domain.usecase.UseCase
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class DifficultyViewModel : KoinComponent, KMMViewModel() {

    private val getDifficultyUseCase: GetDifficultyUseCase by inject()

    private val _state = MutableStateFlow<DifficultyScreenState>(DifficultyScreenState.Idle)
    var state =
        _state.stateIn(
            viewModelScope.coroutineScope,
            WhileViewSubscribed,
            DifficultyScreenState.Idle
        )

    init {
        getDifficulties()
    }

    fun getDifficulties() {
        viewModelScope.coroutineScope.launch {
            getDifficultyUseCase.run(params = UseCase.Nothing).map {
                when (it) {
                    is FlowResult.Success -> {
                        _state.value = DifficultyScreenState.Success(it.data)
                    }

                    is FlowResult.Error -> {
                        _state.value = DifficultyScreenState.Error(it.exception)
                    }

                    FlowResult.Loading -> {
                        _state.value = DifficultyScreenState.Loading
                    }
                }
            }.collectLatest { }
        }
    }

}