package com.norrisboat.ziuq.data.remote.result


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResult(
    @SerialName("id")
    val id: String? = null,
    @SerialName("username")
    val username: String? = null
)