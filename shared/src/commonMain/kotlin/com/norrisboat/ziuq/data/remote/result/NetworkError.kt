package com.norrisboat.ziuq.data.remote.result


import kotlinx.serialization.Serializable

@Serializable
data class NetworkError(
    val success: Boolean? = null,
    val message: String? = null
)