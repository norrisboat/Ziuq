package com.norrisboat.ziuq.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.norrisboat.ziuq.AppDatabase
import com.norrisboat.ziuq.Quiz
import com.norrisboat.ziuq.domain.database.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class QuizDao(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.quizQueries
    private val coroutineContext = Dispatchers.Main

    fun getAllQuizzes(): Flow<List<Quiz>> {
        return dbQuery.getAllQuizs().asFlow().mapToList(coroutineContext)
    }

    fun getRawAllQuizzes(): List<Quiz> {
        return dbQuery.getAllQuizs().executeAsList()
    }

    fun getQuiz(key: String): Flow<Quiz?> {
        return dbQuery.getQuiz(key).asFlow().mapToOneOrNull(coroutineContext)
    }

    fun insertQuiz(quiz: Quiz) {
        dbQuery.insertQuiz(quiz)
    }

    fun insertQuizzes(quizzes: List<Quiz>) {
        quizzes.forEach { quiz ->
            insertQuiz(quiz)
        }
    }

    fun deleteQuiz(key: String) {
        dbQuery.transaction {
            dbQuery.deleteQuiz(key)
        }
    }

    internal fun clearTable() {
        dbQuery.transaction {
            dbQuery.deleteAllQuizs()
        }
    }
}