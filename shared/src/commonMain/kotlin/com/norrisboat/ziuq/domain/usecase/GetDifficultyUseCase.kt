package com.norrisboat.ziuq.domain.usecase

import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.data.ui.QuizDifficulty
import com.norrisboat.ziuq.domain.utils.FlowResult
import com.norrisboat.ziuq.domain.utils.asFlow
import com.norrisboat.ziuq.domain.utils.asFlowResult
import kotlinx.coroutines.flow.Flow

class GetDifficultyUseCase(private val settingsRepository: SettingsRepository) :
    UseCase<List<QuizDifficulty>, UseCase.Nothing>() {

    override suspend fun run(params: Nothing): Flow<FlowResult<List<QuizDifficulty>>> {
        val difficulties = settingsRepository.getDifficulties()
        return if (difficulties.isEmpty()) {
            Result.failure(Throwable("No difficulties found"))
        } else {
            Result.success(difficulties)
        }.asFlow().asFlowResult()
    }

}