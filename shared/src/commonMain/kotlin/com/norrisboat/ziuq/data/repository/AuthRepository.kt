package com.norrisboat.ziuq.data.repository

import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.remote.result.LoginResult
import com.norrisboat.ziuq.data.remote.result.RegisterResult
import com.norrisboat.ziuq.data.remote.service.AuthService
import com.norrisboat.ziuq.domain.utils.getResults
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResult>

    suspend fun register(username: String, password: String): Result<RegisterResult>

}

class AuthRepositoryImpl : KoinComponent, AuthRepository {

    private val authService: AuthService by inject()

    override suspend fun login(loginRequest: LoginRequest): Result<LoginResult> {
        return authService.login(loginRequest).getResults()
    }

    override suspend fun register(
        username: String,
        password: String
    ): Result<RegisterResult> {
        return authService.register(username, password).getResults()
    }

}