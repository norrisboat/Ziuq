package com.norrisboat.ziuq.presentation.home

import com.norrisboat.ziuq.domain.usecase.GetCategoriesUseCase
import com.norrisboat.ziuq.domain.usecase.UseCase
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import com.rickclephas.kmm.viewmodel.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class HomeViewModel : KoinComponent, KMMViewModel() {

    private val getCategoriesUseCase: GetCategoriesUseCase by inject()

    private val _state = MutableStateFlow<HomeScreenState>(viewModelScope, HomeScreenState.Idle)
    @NativeCoroutinesState
    var state =
        _state.stateIn(viewModelScope, WhileViewSubscribed, HomeScreenState.Idle)

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.coroutineScope.launch {
            getCategoriesUseCase.run(params = UseCase.Nothing).map { result ->
                when (result) {
                    is FlowResult.Success -> {
                        _state.update { HomeScreenState.Success(result.data) }
                    }

                    is FlowResult.Error -> {
                        _state.value = HomeScreenState.Error(result.exception)
                    }

                    FlowResult.Loading -> {
                        _state.value = HomeScreenState.Loading
                    }
                }
            }.collectLatest { }
        }
    }

}