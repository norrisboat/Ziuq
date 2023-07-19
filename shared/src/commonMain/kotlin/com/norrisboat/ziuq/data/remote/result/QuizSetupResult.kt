package com.norrisboat.ziuq.data.remote.result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuizSetupResult(
    @SerialName("categories")
    val categories: List<CategoryResult> = emptyList(),
    @SerialName("difficulties")
    val difficulties: List<String> = emptyList(),
    @SerialName("types")
    val typeResults: List<TypeResult> = emptyList()
)