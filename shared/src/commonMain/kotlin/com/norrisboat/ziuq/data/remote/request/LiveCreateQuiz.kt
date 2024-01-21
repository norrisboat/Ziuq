package com.norrisboat.ziuq.data.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LiveCreateQuiz(
    @SerialName("category")
    val category: String? = null,
    @SerialName("difficulty")
    val difficulty: String? = null,
    @SerialName("liveQuizId")
    val liveQuizId: String? = null,
    @SerialName("quizType")
    val quizType: String? = null,
    @SerialName("type")
    val type: String? = null
)