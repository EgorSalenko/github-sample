package io.esalenko.github.sample.app.di

import io.esalenko.github.sample.app.helper.OAuth2AuthorizationHelper
import io.esalenko.github.sample.app.helper.OAuth2TokenHelper
import org.koin.dsl.module


val authModule = module {
    single { OAuth2AuthorizationHelper() }
    single { OAuth2TokenHelper(get(), get()) }
}
