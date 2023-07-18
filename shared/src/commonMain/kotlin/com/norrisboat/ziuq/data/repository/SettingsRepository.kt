package com.norrisboat.ziuq.data.repository

import com.russhwolf.settings.Settings
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface SettingsRepository {

    fun isLoggedIn(): Boolean
    fun setIsLoggedIn(status: Boolean)

    fun getUserId(): String
    fun setUserId(id: String)

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


}

enum class SettingsKey {
    IsLoggedIn,
    UserId,
}