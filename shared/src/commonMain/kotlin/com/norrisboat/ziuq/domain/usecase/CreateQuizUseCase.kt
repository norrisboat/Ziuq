package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.data.remote.request.CreateQuizRequest
import com.norrisboat.ziuq.data.repository.QuizRepository
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlow
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.flow.Flow

class CreateQuizUseCase(private val quizRepository: QuizRepository) :
    UseCase<QuizUI, CreateQuizRequest>() {

    override suspend fun run(params: CreateQuizRequest): Flow<FlowResult<QuizUI>> {
        return quizRepository.createQuiz(params).asFlow().asFlowResult()
    }

}