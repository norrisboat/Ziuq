package com.norrisboat.ziuq.data.remote.request


import kotlinx.serialization.Serializable

@Serializable
data class LiveQuizJoin(
    val liveQuizId: String,
    val type: String,
    val userId: String,
    val name: String
)