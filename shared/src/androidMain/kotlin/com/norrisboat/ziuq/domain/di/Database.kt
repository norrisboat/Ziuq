package com.norrisboat.ziuq.domain.di

import com.norrisboat.ziuq.data.local.CategoryDao
import com.norrisboat.ziuq.data.local.QuestionDao
import com.norrisboat.ziuq.data.local.QuizDao
import com.norrisboat.ziuq.data.local.TypeDao
import com.norrisboat.ziuq.domain.database.DatabaseDriverFactory
import org.koin.dsl.module

actual fun databaseModule() = module {
    single { DatabaseDriverFactory(get()) }
    single { CategoryDao(get()) }
    single { TypeDao(get()) }
    single { QuizDao(get()) }
    single { QuestionDao(get()) }
}