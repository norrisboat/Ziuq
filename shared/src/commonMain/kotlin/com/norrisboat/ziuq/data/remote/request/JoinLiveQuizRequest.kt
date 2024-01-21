package com.norrisboat.ziuq.data.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class JoinLiveQuizRequest(
    @SerialName("liveQuizId")
    val liveQuizId: String? = null,
    @SerialName("userId")
    val userId: String? = null
)