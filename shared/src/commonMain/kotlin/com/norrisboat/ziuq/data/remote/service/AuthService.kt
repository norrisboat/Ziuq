package com.norrisboat.ziuq.data.remote.service

import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.remote.request.RegisterRequest
import com.norrisboat.ziuq.data.remote.result.BaseResult
import com.norrisboat.ziuq.data.remote.result.LoginResult
import com.norrisboat.ziuq.data.remote.result.QuizSetupResult
import com.norrisboat.ziuq.data.remote.result.RegisterResult
import com.norrisboat.ziuq.domain.utils.Endpoint
import com.norrisboat.ziuq.domain.utils.Endpoint.makeEndpoint
import com.norrisboat.ziuq.domain.utils.apiUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
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
        val response = httpClient.post {
            apiUrl(Endpoint.Path.LOGIN.makeEndpoint())
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }

        return response.body()
    }

    override suspend fun register(registerRequest: RegisterRequest): BaseResult<RegisterResult?> {
        val response = httpClient.post {
            apiUrl(Endpoint.Path.REGISTER.makeEndpoint())
            contentType(ContentType.Application.Json)
            setBody(registerRequest)
        }

        return response.body()
    }

    override suspend fun setup(): BaseResult<QuizSetupResult?> {
        val response = httpClient.get {
            apiUrl(Endpoint.Path.QUIZ_SETUP.makeEndpoint())
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }

}