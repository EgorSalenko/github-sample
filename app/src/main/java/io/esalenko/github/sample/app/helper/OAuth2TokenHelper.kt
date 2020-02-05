package io.esalenko.github.sample.app.helper

import io.esalenko.github.sample.app.data.persistence.SharedPreferenceManager
import net.openid.appauth.AuthState


class OAuth2TokenHelper(private val sharedPreferenceManager: SharedPreferenceManager) {

    val hasToken: Boolean = sharedPreferenceManager.readAuthState() != null

    fun persistAuthToken(authState: AuthState) {
        sharedPreferenceManager.writeAuthState(authState)
    }
}
