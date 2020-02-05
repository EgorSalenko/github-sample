package io.esalenko.github.sample.app

import android.app.Application
import io.esalenko.github.sample.app.di.authModule
import io.esalenko.github.sample.app.di.networkModule
import io.esalenko.github.sample.app.di.persistenceModule
import io.esalenko.github.sample.app.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class GithubApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        injectKoin()
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
                    persistenceModule
                )
            )
        }
    }

}
