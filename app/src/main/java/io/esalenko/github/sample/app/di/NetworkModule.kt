package io.esalenko.github.sample.app.di

import com.google.gson.Gson
import io.esalenko.github.sample.app.data.Constants.BASE_URL
import io.esalenko.github.sample.app.data.network.AuthService
import io.esalenko.github.sample.app.data.network.HttpHeaderInterceptor
import io.esalenko.github.sample.app.data.network.ReposService
import io.esalenko.github.sample.app.data.network.SearchService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    fun provideHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: HttpHeaderInterceptor
    ) = OkHttpClient
        .Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    fun provideGson() = Gson()

    fun provideRetrofit(client: OkHttpClient, gson: Gson, baseUrl: String) = Retrofit.Builder()
        .client(client)
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    single { HttpHeaderInterceptor(get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideHttpClient(get(), get()) }
    single { provideGson() }

    single<SearchService> {
        provideRetrofit(
            get(),
            get(),
            baseUrl = BASE_URL
        ).create(SearchService::class.java)
    }

    single<AuthService> {
        provideRetrofit(
            get(),
            get(),
            baseUrl = BASE_URL
        ).create(AuthService::class.java)
    }

    single<ReposService> {
        provideRetrofit(
            get(),
            get(),
            baseUrl = BASE_URL
        ).create(ReposService::class.java)
    }
}
