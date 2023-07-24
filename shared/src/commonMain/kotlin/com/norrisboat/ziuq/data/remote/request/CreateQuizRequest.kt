package com.norrisboat.ziuq.data.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateQuizRequest(
    @SerialName("category")
    val category: String,
    @SerialName("difficulty")
    val difficulty: String,
    @SerialName("type")
    val type: String
)