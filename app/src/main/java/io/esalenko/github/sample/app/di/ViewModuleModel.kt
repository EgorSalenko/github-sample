package io.esalenko.github.sample.app.di

import io.esalenko.github.sample.app.ui.login.LoginViewModel
import io.esalenko.github.sample.app.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
}