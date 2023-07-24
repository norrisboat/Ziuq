package com.norrisboat.ziuq.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.norrisboat.ziuq.AppDatabase
import com.norrisboat.ziuq.Question
import com.norrisboat.ziuq.domain.database.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class QuestionDao(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.questionQueries
    private val coroutineContext = Dispatchers.Main

    fun getAllQuestions(): Flow<List<Question>> {
        return dbQuery.getAllQuestions().asFlow().mapToList(coroutineContext)
    }

    fun getRawAllQuestions(): List<Question> {
        return dbQuery.getAllQuestions().executeAsList()
    }

    fun getQuestion(key: String): Flow<Question?> {
        return dbQuery.getQuestion(key).asFlow().mapToOneOrNull(coroutineContext)
    }

    fun insertQuestion(question: Question) {
        dbQuery.insertQuestion(question)
    }

    fun insertQuestions(questions: List<Question>) {
        questions.forEach { question ->
            insertQuestion(question)
        }
    }

    fun deleteQuestion(key: String) {
        dbQuery.transaction {
            dbQuery.deleteQuestion(key)
        }
    }

    internal fun clearTable() {
        dbQuery.transaction {
            dbQuery.deleteAllQuestions()
        }
    }
}