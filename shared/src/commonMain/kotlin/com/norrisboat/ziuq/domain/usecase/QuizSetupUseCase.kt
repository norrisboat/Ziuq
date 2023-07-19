package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.data.remote.result.QuizSetupResult
import com.norrisboat.ziuq.data.repository.AuthRepository
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlow
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.flow.Flow

class QuizSetupUseCase(private val authRepository: AuthRepository) :
    UseCase<QuizSetupResult, UseCase.Nothing>() {

    override suspend fun run(params: Nothing): Flow<FlowResult<QuizSetupResult>> {
        return authRepository.setup().asFlow().asFlowResult()
    }

}