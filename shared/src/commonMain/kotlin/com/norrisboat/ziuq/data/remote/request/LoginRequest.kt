package com.norrisboat.ziuq.data.remote.request


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    @SerialName("username")
    val username: String? = null,
    @SerialName("password")
    val password: String? = null
)