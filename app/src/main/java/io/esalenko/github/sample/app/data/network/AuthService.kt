package io.esalenko.github.sample.app.data.network

import io.esalenko.github.sample.app.data.model.user.User
import retrofit2.http.GET
import retrofit2.http.Header


interface AuthService {
    @GET("user")
    suspend fun validateToken(@Header("Authorization") token: String): User
}
