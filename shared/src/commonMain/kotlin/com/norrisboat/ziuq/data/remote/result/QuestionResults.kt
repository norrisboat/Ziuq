package com.norrisboat.ziuq.data.remote.result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionResults(
    @SerialName("category")
    val category: String? = "",
    @SerialName("correctAnswer")
    val correctAnswer: String? = "",
    @SerialName("difficulty")
    val difficulty: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("incorrectAnswers")
    val incorrectAnswers: List<String>? = listOf(),
    @SerialName("isNiche")
    val isNiche: Boolean? = false,
    @SerialName("question")
    val question: QuestionText? = QuestionText(),
    @SerialName("regions")
    val regions: List<String>? = listOf(),
    @SerialName("tags")
    val tags: List<String>? = listOf(),
    @SerialName("type")
    val type: String? = ""
)