package com.norrisboat.ziuq.data.remote.service

import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.remote.result.BaseResult
import com.norrisboat.ziuq.data.remote.result.LoginResult
import com.norrisboat.ziuq.data.remote.result.RegisterResult
import com.norrisboat.ziuq.domain.utils.Endpoint
import com.norrisboat.ziuq.domain.utils.apiUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AuthService {

    suspend fun login(loginRequest: LoginRequest): BaseResult<LoginResult?>

    suspend fun register(username: String, password: String): BaseResult<RegisterResult?>

}

class AuthServiceImpl : KoinComponent, AuthService {

    private val httpClient: HttpClient by inject()

    override suspend fun login(loginRequest: LoginRequest): BaseResult<LoginResult?> {
        val response = httpClient.post {
            apiUrl(Endpoint.makeEndpoint(Endpoint.Path.LOGIN))
            contentType(ContentType.Application.Json)
            setBody(loginRequest)
        }

        return response.body()
    }

    override suspend fun register(username: String, password: String): BaseResult<RegisterResult?> {
        val response = httpClient.post {
            apiUrl(Endpoint.makeEndpoint(Endpoint.Path.REGISTER))
            contentType(ContentType.Application.Json)
            setBody(LoginRequest(username, password))
        }

        return response.body()
    }

}