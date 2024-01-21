package com.norrisboat.ziuq.data.remote.request


import kotlinx.serialization.Serializable

@Serializable
data class LiveAnswer(
    val answer: String,
    val liveQuizId: String,
    val score: Int,
    val type: String,
    val username: String
)