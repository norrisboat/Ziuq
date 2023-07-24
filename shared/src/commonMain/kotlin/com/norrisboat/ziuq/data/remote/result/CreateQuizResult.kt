package com.norrisboat.ziuq.data.remote.result


import com.norrisboat.ziuq.Question
import com.norrisboat.ziuq.Quiz
import com.norrisboat.ziuq.data.ui.QuizUI
import com.norrisboat.ziuq.domain.utils.currentDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuizResult(
    @SerialName("questions")
    val questionResults: List<QuestionResult>? = listOf(),
    @SerialName("quizId")
    val quizId: String? = ""
)

fun CreateQuizResult.toQuizUI(): QuizUI {
    return QuizUI(
        id = quizId ?: "",
        questions = questionResults?.map { it.toQuizQuestion() } ?: emptyList()
    )
}

fun CreateQuizResult.toQuiz(results: String? = null): Quiz {
    return Quiz(quizId ?: "", results, currentDate())
}

fun CreateQuizResult.toQuestions(): List<Question> {
    return questionResults?.map { Question(it.questionId ?: "", this.quizId ?: "", currentDate()) } ?: emptyList()
}