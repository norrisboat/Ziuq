package com.norrisboat.ziuq.data.remote.service

import kotlinx.serialization.Serializable

@Serializable
data class QuizReady(
    val category: String,
    val difficulty: String,
    val quizType: String,
    val liveQuizId: String
)

@Serializable
data class QuizAnswer(
    val username: String,
    val answer: String,
    val score: Int,
    val liveQuizId: String
)

@Serializable
data class QuizError(val errorMessage: String)

enum class LiveQuizActionType {
    PLAYER_JOIN,
    WAITING_FOR_OPPONENT,
    READY,
    LOADING_QUESTIONS,
    QUESTIONS_READY,
    START,
    QUESTION,
    SCORE,
    RESULTS,
    SHOW_RESULTS,
    IDLE
}

fun String.getType(): LiveQuizActionType {
    return when (this.lowercase()) {
        "waitingforopponent" -> LiveQuizActionType.WAITING_FOR_OPPONENT
        "ready", "QUIZ_READY", "quiz_ready" -> LiveQuizActionType.READY
        "start" -> LiveQuizActionType.START
        "loadingquestions" -> LiveQuizActionType.LOADING_QUESTIONS
        "question" -> LiveQuizActionType.QUESTION
        "score" -> LiveQuizActionType.SCORE
        "player_join" -> LiveQuizActionType.PLAYER_JOIN
        "results", "quiz_results", "QUIZ_RESULTS" -> LiveQuizActionType.RESULTS
        "show_results", "SHOW_RESULTS" -> LiveQuizActionType.SHOW_RESULTS
        else -> LiveQuizActionType.IDLE
    }
}