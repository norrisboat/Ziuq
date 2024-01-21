package com.norrisboat.ziuq.data.ui

import com.norrisboat.ziuq.domain.utils.Images
import com.norrisboat.ziuq.domain.utils.Labels
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.desc.StringDesc

data class QuizType(val name: StringDesc, val imageResource: ImageResource) {
    companion object {
        val multiplayer = QuizType(Labels().joinCreateQuiz, Images().multiplayer)
        val startQuiz = QuizType(Labels().startQuiz, Images().play)
    }
}
