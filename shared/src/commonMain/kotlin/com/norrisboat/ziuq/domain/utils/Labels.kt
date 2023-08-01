package com.norrisboat.ziuq.domain.utils

import com.norrisboat.ziuq.MR
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

class Labels {
    val app: StringDesc = StringDesc.Resource(MR.strings.app)
    val tagline: StringDesc = StringDesc.Resource(MR.strings.tagline)
    val login: StringDesc = StringDesc.Resource(MR.strings.login)
    val register: StringDesc = StringDesc.Resource(MR.strings.register)
    val username: StringDesc = StringDesc.Resource(MR.strings.username)
    val password: StringDesc = StringDesc.Resource(MR.strings.password)
    val passwordEmpty: StringDesc = StringDesc.Resource(MR.strings.password_not_empty)
    val usernameEmpty: StringDesc = StringDesc.Resource(MR.strings.username_id_not_empty)
    val noAccountRegister: StringDesc = StringDesc.Resource(MR.strings.no_account_register)
    val backButton: StringDesc = StringDesc.Resource(MR.strings.back_button)
    val dearUser: StringDesc = StringDesc.Resource(MR.strings.dear_user)
    val hello: StringDesc = StringDesc.Resource(MR.strings.hello)
    val creatingQuiz: StringDesc = StringDesc.Resource(MR.strings.creating_quiz_message)
    val skip: StringDesc = StringDesc.Resource(MR.strings.skip)
    val congratulations: StringDesc = StringDesc.Resource(MR.strings.congratulations)
    val congratulationsMessage: StringDesc = StringDesc.Resource(MR.strings.congratulations_message)
    val done: StringDesc = StringDesc.Resource(MR.strings.done)

    fun questionNumber(number: Int): StringDesc {
        return StringDesc.ResourceFormatted(MR.strings.question_number, number)
    }

    fun numberQuestion(number: Int): StringDesc {
        return StringDesc.ResourceFormatted(MR.strings.number_question, number)
    }

    fun categoryQuiz(category: String): StringDesc {
        return StringDesc.ResourceFormatted(MR.strings.quiz_category, category)
    }
}