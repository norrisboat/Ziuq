package com.norrisboat.ziuq.domain.di

import com.norrisboat.ziuq.data.remote.service.AuthService
import com.norrisboat.ziuq.data.remote.service.AuthServiceImpl
import com.norrisboat.ziuq.data.remote.service.QuizService
import com.norrisboat.ziuq.data.remote.service.QuizServiceImpl
import com.norrisboat.ziuq.data.repository.AuthRepository
import com.norrisboat.ziuq.data.repository.AuthRepositoryImpl
import com.norrisboat.ziuq.data.repository.LiveQuizRepository
import com.norrisboat.ziuq.data.repository.LiveQuizRepositoryImpl
import com.norrisboat.ziuq.data.repository.QuizDataRepository
import com.norrisboat.ziuq.data.repository.QuizDataRepositoryImpl
import com.norrisboat.ziuq.data.repository.QuizRepository
import com.norrisboat.ziuq.data.repository.QuizRepositoryImpl
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.data.repository.SettingsRepositoryImpl
import com.norrisboat.ziuq.domain.usecase.CreateQuizUseCase
import com.norrisboat.ziuq.domain.usecase.GetCategoriesUseCase
import com.norrisboat.ziuq.domain.usecase.GetDifficultyUseCase
import com.norrisboat.ziuq.domain.usecase.LoginUseCase
import com.norrisboat.ziuq.domain.usecase.QuizSetupUseCase
import com.norrisboat.ziuq.domain.usecase.RegisterUseCase
import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            repositoryModule,
            useCaseModule,
            servicesModule,
            databaseModule()
        )
    }

}

// For iOS
fun initKoin() = initKoin {}

val repositoryModule = module {
    single<AuthRepository> { AuthRepositoryImpl() }
    single<SettingsRepository> { SettingsRepositoryImpl() }
    single<QuizDataRepository> { QuizDataRepositoryImpl() }
    single<QuizRepository> { QuizRepositoryImpl() }
    single<LiveQuizRepository> { LiveQuizRepositoryImpl() }

    single { Settings() }
}

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { QuizSetupUseCase(get()) }
    single { GetCategoriesUseCase(get()) }
    single { GetDifficultyUseCase(get()) }
    single { CreateQuizUseCase(get()) }
}

val servicesModule = module {
    single<AuthService> { AuthServiceImpl() }
    single<QuizService> { QuizServiceImpl() }

    single { json() }
    single { createClient(get()) }
}

fun createClient(json: Json): HttpClient {
    return HttpClient(CIO) {
        install(HttpTimeout) {
            requestTimeoutMillis = 50000
        }
        install(ContentNegotiation) {
            json(json)
        }

        install(WebSockets)

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    print("$message")
                }
            }
            level = LogLevel.ALL
        }
    }
}

fun json() = Json {
    ignoreUnknownKeys = true
    prettyPrint = true
    isLenient = true
}