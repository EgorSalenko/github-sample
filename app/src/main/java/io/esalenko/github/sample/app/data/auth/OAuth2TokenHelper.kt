package io.esalenko.github.sample.app.data.auth

import io.esalenko.github.sample.app.data.model.user.User
import io.esalenko.github.sample.app.data.network.AuthService
import io.esalenko.github.sample.app.data.persistence.SharedPreferenceManager
import net.openid.appauth.AuthState


class OAuth2TokenHelper(
    private val sharedPreferenceManager: SharedPreferenceManager,
    private val authService: AuthService
) {

    val hasToken: Boolean = sharedPreferenceManager.readAuthState() != null

    fun persistAuthToken(authState: AuthState) {
        sharedPreferenceManager.writeAuthState(authState)
    }

    suspend fun validateToken(): User {
        val token = sharedPreferenceManager.readAuthState()?.accessToken
            ?: throw IllegalStateException("Stored access token is empty")
        return authService.validateToken("token $token")
    }
}
