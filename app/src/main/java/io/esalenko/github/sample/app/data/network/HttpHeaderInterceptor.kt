package io.esalenko.github.sample.app.data.network

import io.esalenko.github.sample.app.data.persistence.SharedPreferenceManager
import okhttp3.Interceptor
import okhttp3.Response


class HttpHeaderInterceptor(private val preferenceManager: SharedPreferenceManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val newRequest = oldRequest.newBuilder()
            .addHeader("Authorization", "token ${preferenceManager.readAuthState()?.accessToken}")
            .build()
        return chain.proceed(newRequest)
    }
}
