package com.norrisboat.ziuq.data.repository

import com.norrisboat.ziuq.data.remote.service.QuizService
import com.norrisboat.ziuq.domain.utils.getResults
import com.norrisboat.ziuq.domain.utils.socketURL
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface LiveQuizRepository {

    suspend fun connectToSocket()
    suspend fun receiveMessage(coroutineScope: CoroutineScope): Flow<String>
    suspend fun sendMessage(message: String)
    suspend fun createQuiz(): Result<String?>
    suspend fun joinQuiz(quizCode: String): Result<Boolean>
    suspend fun close()

}

@OptIn(DelicateCoroutinesApi::class)
class LiveQuizRepositoryImpl : KoinComponent, LiveQuizRepository {

    private val client: HttpClient by inject()
    private val quizService: QuizService by inject()
    private val settingsRepository: SettingsRepository by inject()
    private val message = MutableStateFlow("")
    private var clientSession: DefaultClientWebSocketSession? = null

    //TODO: Avoid using global scope
    private var globalScope = GlobalScope
    private var socketJob: Job? = null

    override suspend fun connectToSocket() {
        println("\nNorristest: connectToSocket: $clientSession isActive: ${clientSession?.isActive}")

        try {
            clientSession = client.webSocketSession("$socketURL/v1/quiz/live/quiz")

            println("\nNorristest: NEW connectToSocket: $clientSession isActive: ${clientSession?.isActive}")

            socketJob = globalScope.launch(Dispatchers.IO) {
                clientSession?.incoming?.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        println("\nNorristest: Received from server: ${frame.readText()}")
                        message.value = frame.readText()
                    }
                }
            }
        } catch (e: Exception) {
            println("\nNorristest: connectToSocket Exception: ${e.message}")
            e.printStackTrace()
        }

//        clientSession.send(Frame.Text("{     \"userId\": \"04c49b81-d956-4ed6-ba91-87ef6ee93445\",     \"liveQuizId\": \"BG64CB\",     \"type\" : \"join\" }"))
    }

    override suspend fun receiveMessage(coroutineScope: CoroutineScope): Flow<String> =
        message.stateIn(coroutineScope)

    override suspend fun sendMessage(message: String) {
        clientSession?.send(Frame.Text(message))
    }

    override suspend fun createQuiz(): Result<String?> {
        val result =
            quizService.createLiveQuiz(settingsRepository.getUserId()).getResults()
        val createQuizResult = result.getOrNull()
        return if (result.isSuccess) {
            Result.success(createQuizResult?.liveQuizId)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Something went wrong"))
        }
    }

    override suspend fun joinQuiz(quizCode: String): Result<Boolean> {
        val result =
            quizService.joinLiveQuiz(settingsRepository.getUserId(), quizCode).getResults()
        val createQuizResult = result.getOrNull()
        return if (result.isSuccess && createQuizResult != null && createQuizResult.success == true) {
            Result.success(true)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Something went wrong"))
        }
    }

    override suspend fun close() {
        println("\nNorristest: closeeeeeeeddddd")
        client.close()
        socketJob?.cancel()
    }

}