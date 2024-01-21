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
    val hello: StringDesc = StringDesc.Resource(MR.strings.hello)
    val creatingQuiz: StringDesc = StringDesc.Resource(MR.strings.creating_quiz_message)
    val skip: StringDesc = StringDesc.Resource(MR.strings.skip)
    val congratulations: StringDesc = StringDesc.Resource(MR.strings.congratulations)
    val congratulationsMessage: StringDesc = StringDesc.Resource(MR.strings.congratulations_message)
    val done: StringDesc = StringDesc.Resource(MR.strings.done)
    val startQuiz: StringDesc = StringDesc.Resource(MR.strings.start_quiz)
    val quizCode: StringDesc = StringDesc.Resource(MR.strings.quiz_code)
    val enterQuizCode: StringDesc = StringDesc.Resource(MR.strings.enter_quiz_code)
    val quizCodeEmpty: StringDesc = StringDesc.Resource(MR.strings.quiz_code_empty)
    val share: StringDesc = StringDesc.Resource(MR.strings.share)
    val joinCreateQuiz: StringDesc = StringDesc.Resource(MR.strings.join_create_quiz)
    val createQuiz: StringDesc = StringDesc.Resource(MR.strings.create_quiz)
    val joinQuiz: StringDesc = StringDesc.Resource(MR.strings.join_quiz)
    val copyQuizCode: StringDesc = StringDesc.Resource(MR.strings.copy)
    val youWin: StringDesc = StringDesc.Resource(MR.strings.you_win)
    val youLost: StringDesc = StringDesc.Resource(MR.strings.you_lost)
    val draw: StringDesc = StringDesc.Resource(MR.strings.draw)
    val logout: StringDesc = StringDesc.Resource(MR.strings.logout)
    val more: StringDesc = StringDesc.Resource(MR.strings.more)
    val waitingForOpponent: StringDesc = StringDesc.Resource(MR.strings.waiting_for_opponent)
    val joiningQuiz: StringDesc = StringDesc.Resource(MR.strings.joining_quiz)
    val loading: StringDesc = StringDesc.Resource(MR.strings.loading)
    val loadingQuestions: StringDesc = StringDesc.Resource(MR.strings.loading_questions)
    val creatingQuizMessage: StringDesc = StringDesc.Resource(MR.strings.creating_quiz)
    val quizCodeCopied: StringDesc = StringDesc.Resource(MR.strings.quiz_code_copied)

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