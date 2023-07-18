package com.norrisboat.ziuq.android

import android.app.Application
import com.norrisboat.ziuq.android.di.viewModelModules
import com.norrisboat.ziuq.domain.di.initKoin
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

@DelicateCoroutinesApi
class ZiuqApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@ZiuqApplication)
            modules(viewModelModules)
        }
    }

}