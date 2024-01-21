package com.norrisboat.ziuq.data.remote.result


import com.norrisboat.ziuq.data.ui.QuizUI
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LiveCreateQuizResult(
    @SerialName("questions")
    val questions: List<QuestionResults>? = listOf()
)

fun LiveCreateQuizResult.toQuizUI(): QuizUI {
    return QuizUI(
        id = "",
        questions = questions?.map { it.toQuizQuestion() } ?: emptyList()
    )
}