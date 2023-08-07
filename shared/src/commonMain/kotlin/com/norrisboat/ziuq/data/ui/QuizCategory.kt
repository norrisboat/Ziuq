package com.norrisboat.ziuq.data.ui

import com.norrisboat.ziuq.Category
import com.norrisboat.ziuq.domain.utils.Images
import dev.icerock.moko.resources.ImageResource


data class QuizCategory(val name: String, val key: String, val imageResource: ImageResource) {
    companion object {
        val sample = QuizCategory("Science", "science", Images().science)
    }
}

fun Category.toQuizCategory() : QuizCategory {
    val imageResource = when(this.key) {
        "science" -> Images().science
        "film_and_tv" -> Images().film
        "music" -> Images().music
        "history" -> Images().history
        "geography" -> Images().geography
        "art_and_literature" -> Images().artAndLiterature
        "sport_and_leisure" -> Images().sports
        "general_knowledge" -> Images().generalKnowledge
        "food_and_drink" -> Images().food
        "random" -> Images().random
        else -> Images().science
    }
    return QuizCategory(this.name, this.key, imageResource)
}
