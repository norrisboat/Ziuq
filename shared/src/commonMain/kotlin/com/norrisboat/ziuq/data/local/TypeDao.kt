package com.norrisboat.ziuq.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.norrisboat.ziuq.AppDatabase
import com.norrisboat.ziuq.Type
import com.norrisboat.ziuq.domain.database.DatabaseDriverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TypeDao(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = AppDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.typeQueries
    private val coroutineContext = Dispatchers.Main

    fun getAllTypes(): Flow<List<Type>> {
        return dbQuery.getAllTypes().asFlow().mapToList(coroutineContext)
    }

    fun getRawAllTypes(): List<Type> {
        return dbQuery.getAllTypes().executeAsList()
    }

    fun getType(key: String): Flow<Type?> {
        return dbQuery.gettype(key).asFlow().mapToOneOrNull(coroutineContext)
    }

    fun insertType(type: Type) {
        dbQuery.insertType(type)
    }

    fun insertTypes(types: List<Type>) {
        types.forEach { type ->
            insertType(type)
        }
    }

    fun deleteType(key: String) {
        dbQuery.transaction {
            dbQuery.deletetype(key)
        }
    }

    internal fun clearTable() {
        dbQuery.transaction {
            dbQuery.deleteAlltypes()
        }
    }
}