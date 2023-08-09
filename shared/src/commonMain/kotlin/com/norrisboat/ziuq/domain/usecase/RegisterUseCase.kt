package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.data.remote.request.RegisterRequest
import com.norrisboat.ziuq.data.remote.result.RegisterResult
import com.norrisboat.ziuq.data.repository.AuthRepository
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlow
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(private val authRepository: AuthRepository) :
    UseCase<RegisterResult, RegisterRequest>() {

    override suspend fun run(params: RegisterRequest): Flow<FlowResult<RegisterResult>> {
        return authRepository.register(params).asFlow().asFlowResult()
    }

}