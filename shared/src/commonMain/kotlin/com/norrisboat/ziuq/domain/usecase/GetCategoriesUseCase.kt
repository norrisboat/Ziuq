package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.data.repository.QuizDataRepository
import com.norrisboat.ziuq.data.ui.QuizCategory
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCategoriesUseCase(private val quizDataRepository: QuizDataRepository) :
    UseCase<List<QuizCategory>, UseCase.Nothing>() {

    override suspend fun run(params: Nothing): Flow<FlowResult<List<QuizCategory>>> {
        return quizDataRepository.getCategories().map {
            if (it.isEmpty()) {
                Result.failure(Exception("No categories found"))
            } else {
                Result.success(it)
            }
        }.asFlowResult()
    }

}