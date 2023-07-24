package com.norrisboat.ziuq.data.remote.service

import com.norrisboat.ziuq.data.remote.request.CreateQuizRequest
import com.norrisboat.ziuq.data.remote.result.BaseResult
import com.norrisboat.ziuq.data.remote.result.CreateQuizResult
import com.norrisboat.ziuq.domain.utils.Endpoint
import com.norrisboat.ziuq.domain.utils.makePostRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface QuizService {
    suspend fun createQuiz(
        userId: String,
        quizRequest: CreateQuizRequest
    ): BaseResult<CreateQuizResult?>

}

class QuizServiceImpl : KoinComponent, QuizService {

    private val httpClient: HttpClient by inject()
    override suspend fun createQuiz(
        userId: String,
        quizRequest: CreateQuizRequest
    ): BaseResult<CreateQuizResult?> {
        return httpClient.makePostRequest(quizRequest, Endpoint.Path.CREATE_QUIZ, userId).body()
    }

}