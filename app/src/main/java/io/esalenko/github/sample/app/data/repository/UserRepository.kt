package io.esalenko.github.sample.app.data.repository

import io.esalenko.github.sample.app.data.model.user.User
import io.esalenko.github.sample.app.data.network.AuthService


class UserRepository(private val service: AuthService) {
    suspend fun getUserData(): User = service.getUser()
}
