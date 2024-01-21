package com.norrisboat.ziuq.data.remote.result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateLiveQuizResult(
    @SerialName("data")
    val quizData: CreateLiveQuizData? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("success")
    val success: Boolean? = null
)

@Serializable
data class CreateLiveQuizData(
    @SerialName("liveQuizId")
    val liveQuizId: String? = null,
    @SerialName("userId")
    val userId: String? = null
)