package io.esalenko.github.sample.app.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.esalenko.github.sample.app.data.persistence.SharedPreferenceManager
import net.openid.appauth.AuthState


class LoginViewModel(
    app: Application,
    private val sharedPreferenceManager: SharedPreferenceManager
) : AndroidViewModel(app) {

    fun persistAuthState(authState: AuthState) {
        sharedPreferenceManager.writeAuthState(authState)
    }
}
