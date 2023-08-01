package com.norrisboat.ziuq.presentation.home

import com.norrisboat.ziuq.domain.usecase.GetCategoriesUseCase
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

open class HomeViewModel : KoinComponent, KMMViewModel() {

    private val getCategoriesUseCase: GetCategoriesUseCase by inject()

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Idle)
    var state =
        _state.stateIn(viewModelScope.coroutineScope, WhileViewSubscribed, HomeScreenState.Idle)

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.coroutineScope.launch {
            getCategoriesUseCase.run(params = UseCase.Nothing).map {
                when (it) {
                    is FlowResult.Success -> {
                        _state.value = HomeScreenState.Success(it.data)
                    }

                    is FlowResult.Error -> {
                        _state.value = HomeScreenState.Error(it.exception)
                    }

                    FlowResult.Loading -> {
                        _state.value = HomeScreenState.Loading
                    }
                }
            }.collectLatest { }
        }
    }

}