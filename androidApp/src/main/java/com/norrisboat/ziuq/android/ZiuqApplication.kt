package com.norrisboat.ziuq.android

import android.app.Application
import com.norrisboat.ziuq.android.di.viewModelModules
import com.norrisboat.ziuq.domain.di.initKoin
import kotlinx.coroutines.DelicateCoroutinesApi
import org.koin.android.ext.koin.androidContext

@DelicateCoroutinesApi
class ZiuqApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@ZiuqApplication)
            modules(viewModelModules)
        }
    }

}