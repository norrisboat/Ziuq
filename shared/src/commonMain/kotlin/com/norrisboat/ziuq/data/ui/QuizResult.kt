package com.norrisboat.ziuq.data.ui

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class QuizResult(
    val p1Name: String,
    val p1Image: String,
    val p1Score: Int,
    val p2Name: String,
    val p2Image: String,
    val p2Score: Int,
    val isLiveQuiz: Boolean
) {
    fun encode() = Json.encodeToString(this)

    companion object {
        fun decode(json: String) = Json.decodeFromString<QuizResult>(json)

        val sample: QuizResult = QuizResult(
            p1Image = "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg",
            p1Name = "Player 1",
            p1Score = 40,
            p2Image = "https://global.discourse-cdn.com/monzo/original/3X/8/6/866e6d84e8c756b19050fbe2ca0932858118614c.jpg",
            p2Name = "Player 2",
            p2Score = 100,
            isLiveQuiz = true
        )
    }
}
