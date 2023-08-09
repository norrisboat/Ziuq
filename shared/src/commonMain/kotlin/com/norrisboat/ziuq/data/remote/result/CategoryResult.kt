package com.norrisboat.ziuq.data.remote.result


import com.norrisboat.ziuq.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryResult(
    @SerialName("key")
    val key: String? = null,
    @SerialName("name")
    val name: String? = null
)

fun CategoryResult.toType(): Category {
    return Category(this.key ?: "", this.name ?: "")
}