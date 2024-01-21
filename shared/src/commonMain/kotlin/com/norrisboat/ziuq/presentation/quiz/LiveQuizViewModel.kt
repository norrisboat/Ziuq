package com.norrisboat.ziuq.presentation.quiz

import com.norrisboat.ziuq.data.remote.request.LiveCreateQuiz
import com.norrisboat.ziuq.data.remote.request.LiveQuizJoin
import com.norrisboat.ziuq.data.remote.result.LiveCreateQuizResult
import com.norrisboat.ziuq.data.remote.result.LivePlayer
import com.norrisboat.ziuq.data.remote.result.LiveQuizMessage
import com.norrisboat.ziuq.data.remote.result.toQuizUI
import com.norrisboat.ziuq.data.remote.service.LiveQuizActionType
import com.norrisboat.ziuq.data.remote.service.getType
import com.norrisboat.ziuq.data.repository.LiveQuizRepository
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.SingleEventFlow
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.seconds

open class LiveQuizViewModel : KoinComponent, KMMViewModel() {

    private val liveQuizRepository: LiveQuizRepository by inject()
    private val settingsRepository: SettingsRepository by inject()
    private val json: Json by inject()

    private val _state =
        MutableStateFlow<LiveQuizScreenState>(viewModelScope, LiveQuizScreenState.Idle)

    @NativeCoroutinesState
    var state =
        _state.stateIn(
            viewModelScope,
            WhileViewSubscribed,
            LiveQuizScreenState.Idle
        )

    private val _navigation = SingleEventFlow<LiveQuizScreenNavigation>()
    val navigation = _navigation.shareIn(viewModelScope.coroutineScope, WhileViewSubscribed)

    private val _liveQuizId = MutableStateFlow(viewModelScope, "")
    private val _players = MutableStateFlow(viewModelScope, mutableSetOf<LivePlayer>())
    private val _liveQuizCreateRequest = MutableStateFlow<LiveCreateQuiz?>(viewModelScope, null)
    private val _quizUI = MutableStateFlow<QuizUI?>(viewModelScope, null)

    @NativeCoroutinesState
    var liveQuizId = _liveQuizId.stateIn(viewModelScope, WhileViewSubscribed, "")

    init {
        viewModelScope.coroutineScope.launch {
            liveQuizRepository.receiveMessage(this)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest { message ->
                    try {
                        val liveQuizMessage = json.decodeFromString<LiveQuizMessage>(message)
                        println("\nNorristest: receiveMessage: $message liveQuizMessage: ${liveQuizMessage.chatType} type --> ${liveQuizMessage.chatType.getType()}")
                        when (liveQuizMessage.chatType.getType()) {
                            LiveQuizActionType.WAITING_FOR_OPPONENT -> {
                                _state.update { LiveQuizScreenState.WaitingForOpponent }
                            }

                            LiveQuizActionType.READY -> {
                                val request =
                                    _liveQuizCreateRequest.value?.copy(liveQuizId = _liveQuizId.value)
                                request?.let {
                                    liveQuizRepository.sendMessage(
                                        json.encodeToString(
                                            request
                                        )
                                    )
                                }
                            }

                            LiveQuizActionType.PLAYER_JOIN -> {
                                try {
                                    val player = json.decodeFromString<LivePlayer>(message)
                                    val players = _players.value.toMutableSet()
                                    players.add(player)
                                    _players.update { players }
                                    println("\nNorristest: player: $player")
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            LiveQuizActionType.LOADING_QUESTIONS -> {
                                _state.update { LiveQuizScreenState.LoadingQuestions }
                            }

                            LiveQuizActionType.START, LiveQuizActionType.QUESTION -> {
                                println("\nNorristest: START: ${_quizUI.value}")
                                _quizUI.value?.let { quiz ->
                                    val quizUI = quiz.copy(
                                        player1 = _players.value.first { it.username == settingsRepository.getUserId() },
                                        player2 = _players.value.first { it.username != settingsRepository.getUserId() },
                                        liveQuizId = _liveQuizId.value
                                    )
                                    _navigation.emit(LiveQuizScreenNavigation.QuizReady(quizUI))
                                }
                            }

                            else -> {

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        println("\nNorristest: message: $message Exception: ${e.message}")
                    }

                    try {
                        val quiz = json.decodeFromString<LiveCreateQuizResult>(message)
                        _quizUI.value = quiz.toQuizUI()
//                        println("\nNorristest: _quizUI: ${_quizUI.value}")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }
    }

    fun setupQuiz(categoryKey: String, difficulty: String) {
        _liveQuizCreateRequest.update {
            LiveCreateQuiz(
                categoryKey.lowercase(),
                difficulty.lowercase(),
                "",
                "text_choice",
                "ready"
            )
        }
    }

    fun createQuiz() {
        viewModelScope.coroutineScope.launch {
            _state.update { LiveQuizScreenState.CreatingQuiz }
            val results = liveQuizRepository.createQuiz()
            results.fold(
                onSuccess = { quizId ->
                    _liveQuizId.value = quizId ?: ""
                    _state.update { LiveQuizScreenState.QuizCreated }
                    liveQuizRepository.connectToSocket()
                    delay(1.seconds)
                    liveQuizRepository.sendMessage(
                        Json.encodeToString(
                            LiveQuizJoin(
                                liveQuizId.value,
                                "join",
                                settingsRepository.getUserId(),
                                settingsRepository.getUsername()
                            )
                        )
                    )
                },
                onFailure = {

                }
            )

        }
    }

    fun joinQuiz(quizCode: String) {
        _state.update { LiveQuizScreenState.JoiningQuiz }
        viewModelScope.coroutineScope.launch {
            val results = liveQuizRepository.joinQuiz(quizCode)
            results.fold(
                onSuccess = {
                    _liveQuizId.value = quizCode
                    liveQuizRepository.connectToSocket()
                    liveQuizRepository.sendMessage(
                        Json.encodeToString(
                            LiveQuizJoin(
                                liveQuizId.value,
                                "join",
                                settingsRepository.getUserId(),
                                settingsRepository.getUsername()
                            )
                        )
                    )
                },
                onFailure = {

                }
            )

        }
    }

}