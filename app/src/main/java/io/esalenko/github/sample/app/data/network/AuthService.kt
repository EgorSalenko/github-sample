package io.esalenko.github.sample.app.data.network

import io.esalenko.github.sample.app.data.model.user.User
import retrofit2.http.GET


interface AuthService {
    @GET("user")
    suspend fun getUser(): User
}
