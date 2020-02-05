package io.esalenko.github.sample.app

import android.app.Application
import io.esalenko.github.sample.app.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        injectKoin()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun injectKoin() {
        startKoin {
            androidLogger(level = Level.INFO)
            androidContext(this@GithubApplication)
            modules(
                listOf(
                    authModule,
                    networkModule,
                    viewModelModule,
                    persistenceModule,
                    searchModule
                )
            )
        }
    }

}
