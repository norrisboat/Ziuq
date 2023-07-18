package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.remote.result.LoginResult
import com.norrisboat.ziuq.data.repository.AuthRepository
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlow
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.flow.Flow

class LoginUseCase(private val authRepository: AuthRepository) :
    UseCase<LoginResult, LoginRequest>() {

    override suspend fun run(params: LoginRequest): Flow<FlowResult<LoginResult>> {
        return authRepository.login(params).asFlow().asFlowResult()
    }

}