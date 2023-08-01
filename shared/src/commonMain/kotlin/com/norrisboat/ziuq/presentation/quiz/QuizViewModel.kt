package com.norrisboat.ziuq.presentation.quiz

import com.norrisboat.ziuq.data.ui.QuizQuestion
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.SingleEventFlow
import com.norrisboat.ziuq.domain.utils.WhileViewSubscribed
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import io.github.aakira.napier.Napier
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModel : KoinComponent, KMMViewModel() {

    private var timerJob: Job? = null

    private var _questionIndex: MutableStateFlow<Int> = MutableStateFlow(-1)
    var questionNumber: StateFlow<Int> = _questionIndex.transformLatest {
        emit(it + 1)
    }.stateIn(
        viewModelScope.coroutineScope,
        WhileViewSubscribed, 1
    )

    private var _currentScore: MutableStateFlow<Int> = MutableStateFlow(0)
    var currentScore: StateFlow<Int> = _currentScore.transformLatest {
        emit(it)
    }.stateIn(
        viewModelScope.coroutineScope,
        WhileViewSubscribed,
        0
    )

    private var _timeLeft: MutableStateFlow<Float> = MutableStateFlow(100f)
    var timeLeft: StateFlow<Float> = _timeLeft.transformLatest {
        emit(it)
    }.stateIn(
        viewModelScope.coroutineScope,
        WhileViewSubscribed,
        100f
    )

    private var questions: MutableList<QuizQuestion> = mutableListOf()
    private var _currentQuestion: MutableStateFlow<QuizQuestion?> = MutableStateFlow(null)

    val currentQuestion = _currentQuestion.stateIn(
        viewModelScope.coroutineScope,
        WhileViewSubscribed, null
    )

    var completed = SingleEventFlow<Unit>()

    init {
        viewModelScope.coroutineScope.launch {
            _questionIndex.collectLatest { index ->
                if (questions.isNotEmpty()) {
                    _currentQuestion.value = questions[index]
                    startCountDown()
                }
            }
        }
    }

    fun setQuizUI(quizUI: QuizUI) {
        questions.clear()
        questions.addAll(quizUI.questions)
        _questionIndex.value = 0
    }

    fun nextQuestion(answer: String = "") {
        viewModelScope.coroutineScope.launch {
            timerJob?.cancel()
            if (answer.isNotBlank() && questions[_questionIndex.value].correctAnswer == answer) {
                _currentScore.update { it + 10 }
            }
            if (_questionIndex.value == questions.lastIndex) {
                completed.tryEmit(Unit)
            } else {
                _questionIndex.update { it + 1 }
            }
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
            countDownFlow(30f).collectLatest {
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

        if (count == 0f) {
            nextQuestion("")
        }
    }
}