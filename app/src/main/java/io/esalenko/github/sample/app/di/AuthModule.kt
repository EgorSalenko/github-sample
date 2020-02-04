package io.esalenko.github.sample.app.di

import io.esalenko.github.sample.app.helper.OAuth2AuthorizationHelper
import io.esalenko.github.sample.app.ui.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val authModule = module {
    viewModel { LoginViewModel(get(), get()) }
    // TODO :: scoped?
    single { OAuth2AuthorizationHelper() }
}
