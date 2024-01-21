package com.norrisboat.ziuq.data.ui

import com.norrisboat.ziuq.data.remote.result.LivePlayer
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class QuizUI(
    val id: String,
    val questions: List<QuizQuestion>,
    var liveQuizId: String = "",
    val player1: LivePlayer? = null,
    val player2: LivePlayer? = null
) {
    fun encode() = Json.encodeToString(this)

    fun isLiveQuiz() = liveQuizId.isNotBlank()

    companion object {
        fun decode(json: String) = Json.decodeFromString<QuizUI>(json)

        val sample: QuizUI = QuizUI("", listOf(QuizQuestion.sample))
    }
}
