package com.norrisboat.ziuq.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<Result<T>>.asFlowResult(): Flow<FlowResult<T>> {
    return this
        .map {
            it.fold(
                onSuccess = { result ->
//                    delay(5000)
                    FlowResult.Success(result)
                },
                onFailure = { error ->
//                    delay(5000)
                    FlowResult.Error(error.message ?: "Something went wrong")
                }
            )
        }
        .onStart { emit(FlowResult.Loading) }
        .catch { emit(FlowResult.Error(it.message ?: "")) }
}

fun <T> Result<T>.asFlow() = flow<Result<T>> {
    emit(this@asFlow)
}

sealed interface FlowResult<out T> {
    data class Success<T>(val data: T) : FlowResult<T>
    data class Error(val exception: String) : FlowResult<Nothing>
    object Loading : FlowResult<Nothing>
}