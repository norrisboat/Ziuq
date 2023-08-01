package com.norrisboat.ziuq.data.ui

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class QuizUI(
    val id: String,
    val questions: List<QuizQuestion>
) {
    fun encode() = Json.encodeToString(this)

    companion object {
        fun decode(json: String) = Json.decodeFromString<QuizUI>(json)

        val sample: QuizUI = QuizUI("", listOf(QuizQuestion.sample))
    }
}
