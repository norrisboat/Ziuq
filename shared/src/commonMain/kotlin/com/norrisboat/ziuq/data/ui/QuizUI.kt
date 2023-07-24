package com.norrisboat.ziuq.data.ui

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class QuizUI(
    val id: String,
    val questions: List<QuizQuestion>
) {
    fun encode() = Json.encodeToString(this)
    fun decode(json: String) = Json.decodeFromString<QuizUI>(json)
}
