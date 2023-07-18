package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class UseCase<out Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Flow<FlowResult<Type>>

    suspend operator fun invoke(
        params: Params,
        coroutineContext: CoroutineContext = Dispatchers.Main
    ) = flow {
        coroutineScope {
            launch(coroutineContext) {
                emit(run(params))
            }
        }
    }
}