package com.norrisboat.ziuq.data.ui

import com.norrisboat.ziuq.domain.utils.Images
import dev.icerock.moko.resources.ImageResource

data class QuizDifficulty(val name: String, val key: String, val imageResource: ImageResource) {
    companion object {
        val sample = QuizDifficulty("Easy", "easy", Images().easy)
    }
}

fun String.toQuizCategory(): QuizDifficulty {
    val imageResource = when (this.lowercase()) {
        "easy" -> Images().easy
        "medium" -> Images().medium
        else -> Images().hard
    }
    return QuizDifficulty(this, this.lowercase(), imageResource)
}
