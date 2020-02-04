package io.esalenko.github.sample.app.di

import io.esalenko.github.sample.app.data.persistence.SharedPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val persistenceModule = module {
    single { SharedPreferenceManager(androidContext()) }
}