package net.achris.mediumnothanks

import android.app.Application
import net.achris.mediumnothanks.di.storeModule
import net.achris.mediumnothanks.di.uiModule
import net.achris.mediumnothanks.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(listOf(storeModule, viewModelModule, uiModule))
        }
    }
}