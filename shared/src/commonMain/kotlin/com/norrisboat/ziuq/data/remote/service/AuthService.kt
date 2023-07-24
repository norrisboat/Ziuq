package com.norrisboat.ziuq.data.remote.service

import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.remote.request.RegisterRequest
import com.norrisboat.ziuq.data.remote.result.BaseResult
import com.norrisboat.ziuq.data.remote.result.LoginResult
import com.norrisboat.ziuq.data.remote.result.QuizSetupResult
import com.norrisboat.ziuq.data.remote.result.RegisterResult
import com.norrisboat.ziuq.domain.utils.Endpoint
import com.norrisboat.ziuq.domain.utils.makeGetRequest
import com.norrisboat.ziuq.domain.utils.makePostRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AuthService {

    suspend fun login(loginRequest: LoginRequest): BaseResult<LoginResult?>

    suspend fun register(registerRequest: RegisterRequest): BaseResult<RegisterResult?>

    suspend fun setup(): BaseResult<QuizSetupResult?>

}

class AuthServiceImpl : KoinComponent, AuthService {

    private val httpClient: HttpClient by inject()

    override suspend fun login(loginRequest: LoginRequest): BaseResult<LoginResult?> {
        return httpClient.makePostRequest(loginRequest, Endpoint.Path.LOGIN).body()
    }

    override suspend fun register(registerRequest: RegisterRequest): BaseResult<RegisterResult?> {
        return httpClient.makePostRequest(registerRequest, Endpoint.Path.REGISTER).body()
    }

    override suspend fun setup(): BaseResult<QuizSetupResult?> {
        return httpClient.makeGetRequest(endPointPath = Endpoint.Path.QUIZ_SETUP).body()
    }

}