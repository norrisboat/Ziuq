package com.norrisboat.ziuq.presentation.quiz

import com.norrisboat.ziuq.data.remote.request.LiveAnswer
import com.norrisboat.ziuq.data.remote.result.LiveQuestion
import com.norrisboat.ziuq.data.remote.result.LiveQuizMessage
import com.norrisboat.ziuq.data.remote.result.LiveResults
import com.norrisboat.ziuq.data.remote.result.LiveScore
import com.norrisboat.ziuq.data.remote.service.LiveQuizActionType
import com.norrisboat.ziuq.data.remote.service.getType
import com.norrisboat.ziuq.data.repository.LiveQuizRepository
import com.norrisboat.ziuq.data.repository.SettingsRepository
import com.norrisboat.ziuq.data.ui.QuizQuestion
import com.norrisboat.ziuq.data.ui.QuizResult
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.SingleEventFlow
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.norrisboat.ziuq.domain.utils.toUserImage
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmm.viewmodel.coroutineScope
import com.rickclephas.kmm.viewmodel.stateIn
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModel : KoinComponent, KMMViewModel() {

    private val liveQuizRepository: LiveQuizRepository by inject()
    private val settingsRepository: SettingsRepository by inject()
    private val json: Json by inject()

    private var timerJob: Job? = null

    private var _liveQuizId = MutableStateFlow(viewModelScope, "")
    private var _questionIndex = MutableStateFlow(viewModelScope, -1)

    @NativeCoroutinesState
    var questionNumber: StateFlow<Int> = _questionIndex.transformLatest {
        emit(it + 1)
    }.stateIn(
        viewModelScope,
        WhileViewSubscribed, 1
    )

    private var _currentScore = MutableStateFlow(viewModelScope, 0)
    private var _opponentScore = MutableStateFlow(viewModelScope, 0)
    private var _opponentAnswerStore = MutableStateFlow(viewModelScope, "")
    private var _opponentAnswer = MutableStateFlow(viewModelScope, "")

    @NativeCoroutinesState
    var currentScore: StateFlow<Int> = _currentScore.transformLatest {
        emit(it)
    }.stateIn(
        viewModelScope,
        WhileViewSubscribed,
        0
    )

    @NativeCoroutinesState
    var opponentScore: StateFlow<Int> = _opponentScore.transformLatest {
        emit(it)
    }.stateIn(
        viewModelScope,
        WhileViewSubscribed,
        0
    )

    @NativeCoroutinesState
    var opponentAnswer: StateFlow<String> = _opponentAnswer.transformLatest {
        emit(it)
    }.stateIn(
        viewModelScope,
        WhileViewSubscribed,
        ""
    )

    private var _timeLeft = MutableStateFlow(viewModelScope, 100f)

    @NativeCoroutinesState
    var timeLeft: StateFlow<Float> = _timeLeft.transformLatest {
        emit(it)
    }.stateIn(
        viewModelScope,
        WhileViewSubscribed,
        100f
    )

    private var questions: MutableList<QuizQuestion> = mutableListOf()

    private var _currentQuestion = MutableStateFlow<QuizQuestion?>(viewModelScope, null)

    @NativeCoroutinesState
    val currentQuestion = _currentQuestion.stateIn(
        viewModelScope,
        WhileViewSubscribed, null
    )

    var completed = SingleEventFlow<Unit>()

    private var _isCompleted = MutableStateFlow(viewModelScope, false)

    @NativeCoroutinesState
    val isCompleted = _isCompleted.stateIn(
        viewModelScope,
        WhileViewSubscribed, false
    )


    init {
        viewModelScope.coroutineScope.launch {
            _questionIndex.collectLatest { index ->
                if (questions.isNotEmpty()) {
                    _currentQuestion.update { questions[index] }
                    _opponentAnswer.update { "" }
                    _opponentAnswerStore.update { "" }
                    startCountDown()
                }
            }
        }
        viewModelScope.coroutineScope.launch {
            liveQuizRepository.receiveMessage(this)
                .distinctUntilChanged()
                .collectLatest { message ->
                    try {
                        val liveQuizMessage = json.decodeFromString<LiveQuizMessage>(message)
                        when (liveQuizMessage.chatType.getType()) {
                            LiveQuizActionType.QUESTION -> {
                                try {
                                    val liveQuestion = json.decodeFromString<LiveQuestion>(message)
                                    nextQuestion("", index = liveQuestion.questionIndex)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    nextQuestion("")
                                }
                            }

                            LiveQuizActionType.SCORE -> {
                                try {
                                    val liveScore = json.decodeFromString<LiveScore>(message)
                                    _opponentScore.update { liveScore.opponentScore }
                                    _opponentAnswerStore.update { liveScore.answer }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            LiveQuizActionType.RESULTS -> {
                                try {
                                    liveQuizRepository.close()
                                    val liveResults = json.decodeFromString<LiveResults>(message)
                                    _opponentScore.update { liveResults.opponentScore }
                                    completed.tryEmit(Unit)
                                    _isCompleted.value = true
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }

                            LiveQuizActionType.SHOW_RESULTS -> {
                                _opponentAnswer.update { _opponentAnswerStore.value }
                                timerJob?.cancel()
                            }

                            else -> {

                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
        }
    }

    fun setQuizUI(quizUI: QuizUI) {
        questions.clear()
        questions.addAll(quizUI.questions)
        _questionIndex.value = 0
        if (quizUI.liveQuizId.isNotBlank()) {
            _liveQuizId.update { quizUI.liveQuizId }
        }
    }

    fun nextQuestion(answer: String = "", index: Int? = null) {
        viewModelScope.coroutineScope.launch {
            timerJob?.cancel()
            if (answer.isNotBlank() && questions[_questionIndex.value].correctAnswer == answer) {
                _currentScore.update { it + 10 }
            }
            if (_questionIndex.value == questions.lastIndex) {
                completed.tryEmit(Unit)
                _isCompleted.value = true
            } else {
                _questionIndex.update { index ?: (it + 1) }
            }
        }
    }

    fun submitAnswer(answer: String = "") {
        viewModelScope.coroutineScope.launch {
            if (answer.isNotBlank() && questions[_questionIndex.value].correctAnswer == answer) {
                _currentScore.update { it + 10 }
            }
            _opponentAnswer.update { _opponentAnswerStore.value }
            val liveAnswer = LiveAnswer(
                answer = answer,
                liveQuizId = _liveQuizId.value,
                score = _currentScore.value,
                type = "answer",
                username = settingsRepository.getUserId()
            )
            liveQuizRepository.sendMessage(json.encodeToString(liveAnswer))
        }
    }

    fun previousQuestion() {
        viewModelScope.coroutineScope.launch {
            if (_questionIndex.value != 0) {
                _questionIndex.update { it - 1 }
            }
        }
    }

    private fun startCountDown() {
        _timeLeft.update { 100f }
        timerJob?.cancel()
        timerJob = viewModelScope.coroutineScope.launch {
            countDownFlow(15f).collectLatest {
                _timeLeft.value = it
            }
        }
    }

    private fun countDownFlow(
        start: Float,
        delayInSeconds: Long = 1_000L,
    ) = flow {
        var count = start
        while (count >= 0f) {
            emit(count--)
            delay(delayInSeconds)
        }

        if (count == -1f) {
            if (_liveQuizId.value.isBlank()) {
                nextQuestion("")
            }
        }
    }

    fun getUserId() = settingsRepository.getUserId()

    fun quizResult(quizUI: QuizUI) = QuizResult(
        p1Name = quizUI.player1?.name ?: settingsRepository.getUsername(),
        p1Image = quizUI.player1?.image?.toUserImage() ?: "",
        p1Score = _currentScore.value,
        p2Name = quizUI.player2?.name ?: "",
        p2Image = quizUI.player2?.image?.toUserImage() ?: "",
        p2Score = _opponentScore.value,
        isLiveQuiz = quizUI.isLiveQuiz()
    )

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineScope.launch {
            liveQuizRepository.close()
        }
    }
}