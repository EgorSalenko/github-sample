package io.esalenko.github.sample.app.di

import io.esalenko.github.sample.app.data.auth.OAuth2AuthorizationHelper
import io.esalenko.github.sample.app.data.auth.OAuth2TokenHelper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val authModule = module {
    single {
        OAuth2AuthorizationHelper(androidContext())
    }
    single {
        OAuth2TokenHelper(get(), get())
    }
}
