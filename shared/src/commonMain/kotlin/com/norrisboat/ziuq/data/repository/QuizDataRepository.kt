package com.norrisboat.ziuq.data.repository

import com.norrisboat.ziuq.data.local.CategoryDao
import com.norrisboat.ziuq.data.ui.QuizCategory
import com.norrisboat.ziuq.data.ui.toQuizCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface QuizDataRepository {

    suspend fun getCategories(): Flow<List<QuizCategory>>

}

class QuizDataRepositoryImpl : KoinComponent, QuizDataRepository {

    private val categoryDao: CategoryDao by inject()

    override suspend fun getCategories(): Flow<List<QuizCategory>> {
        return categoryDao.getAllCategories()
            .map { it.map { category -> category.toQuizCategory() } }.distinctUntilChanged()
    }

}