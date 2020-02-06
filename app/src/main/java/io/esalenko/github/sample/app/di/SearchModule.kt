package io.esalenko.github.sample.app.di

import io.esalenko.github.sample.app.data.repository.SearchRepository
import org.koin.dsl.module


val searchModule = module {
    single { SearchRepository(get(), get(), get()) }
}
