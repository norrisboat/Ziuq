package com.norrisboat.ziuq.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.norrisboat.ziuq.AppDatabase
import com.norrisboat.ziuq.Category
import com.norrisboat.ziuq.domain.database.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class CategoryDao(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.categoryQueries
    private val coroutineContext = Dispatchers.Main

    fun getAllCategories(): Flow<List<Category>> {
        return dbQuery.getAllcategories().asFlow().mapToList(coroutineContext)
    }

    fun getRawAllCategories(): List<Category> {
        return dbQuery.getAllcategories().executeAsList()
    }

    fun getCategory(key: String): Flow<Category?> {
        return dbQuery.getcategory(key).asFlow().mapToOneOrNull(coroutineContext)
    }

    fun insertCategory(category: Category) {
        dbQuery.insertcategory(category)
    }

    fun insertCategories(categories: List<Category>) {
        categories.forEach { category ->
            insertCategory(category)
        }
    }

    fun deleteCategory(key: String) {
        dbQuery.transaction {
            dbQuery.deletecategory(key)
        }
    }

    internal fun clearTable() {
        dbQuery.transaction {
            dbQuery.deleteAllcategorys()
        }
    }
}