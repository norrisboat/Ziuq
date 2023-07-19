package com.norrisboat.ziuq.data.repository

import com.norrisboat.ziuq.data.local.CategoryDao
import com.norrisboat.ziuq.data.local.TypeDao
import com.norrisboat.ziuq.data.remote.request.LoginRequest
import com.norrisboat.ziuq.data.remote.request.RegisterRequest
import com.norrisboat.ziuq.data.remote.result.LoginResult
import com.norrisboat.ziuq.data.remote.result.QuizSetupResult
import com.norrisboat.ziuq.data.remote.result.RegisterResult
import com.norrisboat.ziuq.data.remote.result.toType
import com.norrisboat.ziuq.data.remote.service.AuthService
import com.norrisboat.ziuq.domain.utils.getResults
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface AuthRepository {

    suspend fun login(loginRequest: LoginRequest): Result<LoginResult>

    suspend fun register(registerRequest: RegisterRequest): Result<RegisterResult>

    suspend fun setup(): Result<QuizSetupResult>

}

class AuthRepositoryImpl : KoinComponent, AuthRepository {

    private val authService: AuthService by inject()
    private val settingsRepository: SettingsRepository by inject()
    private val categoryDao: CategoryDao by inject()
    private val typeDao: TypeDao by inject()
    override suspend fun login(loginRequest: LoginRequest): Result<LoginResult> {
        return authService.login(loginRequest).getResults()
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<RegisterResult> {
        return authService.register(registerRequest).getResults()
    }

    override suspend fun setup(): Result<QuizSetupResult> {
        val result = authService.setup().getResults()
        if (result.isSuccess) {
            result.getOrNull()?.let { quizSetupResult ->
                settingsRepository.setDifficulties(quizSetupResult.difficulties.joinToString())
                categoryDao.insertCategories(quizSetupResult.categories.map { it.toType() })
                typeDao.insertTypes(quizSetupResult.typeResults.map { it.toType() })
            }
        }
        return result
    }

}