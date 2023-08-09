package com.norrisboat.ziuq.data.ui

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val options: List<String>,
    val type: String
) {
    companion object {
        val sample: QuizQuestion = QuizQuestion(
            "", "What is a bird?",
            "Animal",
            listOf("Person", "Object", "Town"),
            listOf("Person", "Object", "Town", "Animal"),
            "text"
        )
    }
}
