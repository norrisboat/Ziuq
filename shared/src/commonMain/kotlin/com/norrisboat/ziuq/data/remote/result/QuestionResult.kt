package com.norrisboat.ziuq.data.remote.result


import com.norrisboat.ziuq.data.ui.QuizQuestion
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionResult(
    @SerialName("createdAt")
    val createdAt: String? = "",
    @SerialName("id")
    val id: String? = "",
    @SerialName("questionId")
    val questionId: String? = "",
    @SerialName("questionResult")
    val questionResults: QuestionResults? = QuestionResults(),
    @SerialName("quizId")
    val quizId: String? = ""
)

fun QuestionResult.toQuizQuestion(): QuizQuestion {
    return QuizQuestion(
        id = questionId ?: "",
        question = questionResults?.question?.text ?: "",
        correctAnswer = questionResults?.correctAnswer ?: "",
        type = questionResults?.type ?: "",
        incorrectAnswers = questionResults?.incorrectAnswers ?: emptyList(),
        options = ((questionResults?.incorrectAnswers
            ?: emptyList()) + (questionResults?.correctAnswer ?: "")).shuffled().subList(0, 4)
    )
}

fun QuestionResults.toQuizQuestion(): QuizQuestion {
    return QuizQuestion(
        id = id ?: "",
        question = question?.text ?: "",
        correctAnswer = correctAnswer ?: "",
        type = type ?: "",
        incorrectAnswers = incorrectAnswers ?: emptyList(),
        options = ((incorrectAnswers
            ?: emptyList()) + (correctAnswer ?: "")).shuffled().subList(0, 4)
    )
}