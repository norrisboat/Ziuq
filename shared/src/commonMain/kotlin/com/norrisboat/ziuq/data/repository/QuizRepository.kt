package com.norrisboat.ziuq.data.repository

import com.norrisboat.ziuq.data.local.QuestionDao
import com.norrisboat.ziuq.data.local.QuizDao
import com.norrisboat.ziuq.data.remote.request.CreateQuizRequest
import com.norrisboat.ziuq.data.remote.result.toQuestions
import com.norrisboat.ziuq.data.remote.result.toQuiz
import com.norrisboat.ziuq.data.remote.result.toQuizUI
import com.norrisboat.ziuq.data.remote.service.QuizService
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.getResults
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface QuizRepository {

    suspend fun createQuiz(createQuizRequest: CreateQuizRequest): Result<QuizUI>

}

class QuizRepositoryImpl : KoinComponent, QuizRepository {

    private val quizService: QuizService by inject()
    private val quizDao: QuizDao by inject()
    private val questionDao: QuestionDao by inject()
    private val settingsRepository: SettingsRepository by inject()

    override suspend fun createQuiz(createQuizRequest: CreateQuizRequest): Result<QuizUI> {
        val result =
            quizService.createQuiz(settingsRepository.getUserId(), createQuizRequest).getResults()
        val createQuizResult = result.getOrNull()
        return if (result.isSuccess && createQuizResult != null) {
            quizDao.insertQuiz(createQuizResult.toQuiz())
            questionDao.insertQuestions(createQuizResult.toQuestions())
            Result.success(createQuizResult.toQuizUI())
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Something went wrong"))
        }
    }

}