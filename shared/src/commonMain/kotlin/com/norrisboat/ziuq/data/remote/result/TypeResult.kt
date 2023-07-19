package com.norrisboat.ziuq.data.remote.result


import com.norrisboat.ziuq.Type
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeResult(
    @SerialName("key")
    val key: String? = null,
    @SerialName("name")
    val name: String? = null
)

fun TypeResult.toType() : Type {
    return Type(this.key ?: "", this.name ?: "")
}