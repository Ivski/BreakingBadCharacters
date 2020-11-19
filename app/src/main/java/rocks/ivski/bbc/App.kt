package rocks.ivski.bbc

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rocks.ivski.bbc.di.appModule
import rocks.ivski.bbc.di.repoModule
import rocks.ivski.bbc.di.viewModelModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}