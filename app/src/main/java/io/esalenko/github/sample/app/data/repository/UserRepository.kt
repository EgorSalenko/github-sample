package io.esalenko.github.sample.app.data.repository

import io.esalenko.github.sample.app.data.model.repos.UserRepo
import io.esalenko.github.sample.app.data.model.user.User
import io.esalenko.github.sample.app.data.network.AuthService
import io.esalenko.github.sample.app.data.network.ReposService


class UserRepository(private val authService: AuthService, private val repoService: ReposService) {
    suspend fun getUserData(): User = authService.getUser()
    suspend fun getUserRepos(): Array<UserRepo> = repoService.getUserRepos()
}
