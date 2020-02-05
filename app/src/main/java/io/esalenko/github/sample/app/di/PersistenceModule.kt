package io.esalenko.github.sample.app.di

import android.content.Context
import io.esalenko.github.sample.app.data.db.LocalRoomDatabase
import io.esalenko.github.sample.app.data.persistence.SharedPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val persistenceModule = module {
    fun provideRoomDatabase(ctx: Context) = LocalRoomDatabase.getInstance(ctx)
    fun provideSearchDao(db: LocalRoomDatabase) = db.searchDao
    single { SharedPreferenceManager(androidContext()) }
    single { provideRoomDatabase(androidContext()) }
    single { provideSearchDao(get()) }
}
