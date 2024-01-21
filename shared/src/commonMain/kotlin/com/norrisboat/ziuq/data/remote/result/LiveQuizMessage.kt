package com.norrisboat.ziuq.data.remote.result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LiveQuizMessage(
    @SerialName("type")
    val type: String? = null,
    @SerialName("liveQuizActionType")
    val liveQuizActionType: String? = null
) {
    val chatType = type?.split(".")?.lastOrNull() ?: liveQuizActionType ?: "error"
}

@Serializable
data class LiveScore(
    val opponentScore: Int,
    val answer: String
)

@Serializable
data class LiveQuestion(
    val questionIndex: Int
)

@Serializable
data class LiveResults(val opponentScore: Int)

@Serializable
data class LivePlayer(
    val username: String,
    val name: String,
    val image: String
)