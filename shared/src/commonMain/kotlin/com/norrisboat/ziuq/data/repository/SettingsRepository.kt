package com.norrisboat.ziuq.data.repository

import com.norrisboat.ziuq.data.ui.QuizDifficulty
import com.norrisboat.ziuq.data.ui.toQuizCategory
import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SettingsRepository {

    fun isLoggedIn(): Boolean
    fun setIsLoggedIn(status: Boolean)

    fun getUserId(): String
    fun setUserId(id: String)

    fun getUsername(): String
    fun setUsername(username: String)

    fun getDifficulties(): List<QuizDifficulty>
    fun setDifficulties(difficulties: String)

    fun logout()

}

class SettingsRepositoryImpl : KoinComponent, SettingsRepository {

    private val settings: Settings by inject()

    override fun isLoggedIn(): Boolean {
        return settings.getBoolean(SettingsKey.IsLoggedIn.name, false)
    }

    override fun setIsLoggedIn(status: Boolean) {
        settings.putBoolean(SettingsKey.IsLoggedIn.name, status)
    }

    override fun getUserId(): String {
        return settings.getString(SettingsKey.UserId.name, "")
    }

    override fun setUserId(id: String) {
        settings.putString(SettingsKey.UserId.name, id)
    }

    override fun getUsername(): String {
        return settings.getString(SettingsKey.Username.name, "")
    }

    override fun setUsername(username: String) {
        settings.putString(SettingsKey.Username.name, username)
    }

    override fun getDifficulties(): List<QuizDifficulty> {
        return settings.getString(SettingsKey.Difficulty.name, "").split(",")
            .map { it.trim().toQuizCategory() }
    }

    override fun setDifficulties(difficulties: String) {
        settings.putString(SettingsKey.Difficulty.name, difficulties)
    }

    override fun logout() {
        settings.clear()
    }


}

enum class SettingsKey {
    IsLoggedIn,
    UserId,
    Difficulty,
    Username,
}